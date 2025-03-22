package com.foodDelivery.api.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.foodDelivery.api.exception.ParseObservationTimeException;
import com.foodDelivery.api.exception.WeatherDataReadingException;
import com.foodDelivery.api.model.City;
import com.foodDelivery.api.model.WeatherData;
import com.foodDelivery.api.repository.CityRepository;
import com.foodDelivery.api.repository.WeatherDataRepository;
import com.foodDelivery.api.service.WeatherReadingService;
import com.foodDelivery.api.xml.Observations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of WeatherReadingService interface that fetches and processes weather data from external API.
 *
 * <p>
 *     The service uses RestTemplate to retrieve XML data and XmlMapper to
 *     deserialize the XML into Observations and Station objects. It then takes that information and
 *     saves the WeatherData according to the city.
 * </p>
 */
@Service
public class WeatherReadingServiceImpl implements WeatherReadingService {
    private static final String OBSERVATIONS_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;
    private final CityRepository cityRepository;
    private final WeatherDataRepository weatherDataRepository;

    public WeatherReadingServiceImpl(CityRepository cityRepository, WeatherDataRepository weatherDataRepository) {
        this.cityRepository = cityRepository;
        this.weatherDataRepository = weatherDataRepository;
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }


    /**
     * Method scheduled to fetch weather data from the external API every 15 minutes.
     *
     * <p>
     *     It retrieves the XML response from the weather API.
     *     Deserializes the XML into Observations objects.
     *     Parses the observation timestamp into a LocalDateTime.
     *     Retrieves all WMO codes from the database and filters stations data by wmo code.
     *     For each matching station it looks for the correct City and saves the WeatherData.
     * </p>
     */
    @Override
    @Scheduled(cron = "0 15 * * * *")
    public void readWeatherData() {
        try {
            String responseXml = restTemplate.getForObject(OBSERVATIONS_URL, String.class);
            Observations observations = xmlMapper.readValue(responseXml, Observations.class);
            LocalDateTime observationTime = parseObservationTime(observations.getTimestamp());

            List<String> wmoCodes = cityRepository.findAllWmoCodes();

            observations.getStations().stream()
                    .filter(station -> wmoCodes.contains(station.getWmoCode()))
                    .forEach(station -> {
                        Optional<City> city = cityRepository.findByWmoCode(station.getWmoCode());

                        if (city.isEmpty()) {
                            throw new WeatherDataReadingException("The station with wmoCode " + station.getWmoCode() + " was not found");
                        }

                        WeatherData weatherData = new WeatherData();
                        weatherData.setCity(city.get());
                        weatherData.setAirTemperature(station.getAirTemperature());
                        weatherData.setWindSpeed(station.getWindSpeed());
                        weatherData.setWeatherPhenomenon(station.getPhenomenon());
                        weatherData.setObservationTime(observationTime != null ? observationTime : LocalDateTime.now());
                        weatherDataRepository.save(weatherData);
                    });

        } catch (Exception e) {
            throw new WeatherDataReadingException("Could not process the weather data from the API.", e);
        }
    }

    /**
     * Parses the observation timestamp from a String into a LocalDateTime.
     *
     *<p>
     *     The timestamp is given in Unix epoch seconds.
     *     It is converted into LocalDateTime using the Europe/Tallinn timezone.
     *</p>
     * @param timestamp the observation timestamp as string.
     * @return the LocalDateTime.
     */
    @Override
    public LocalDateTime parseObservationTime(String timestamp) {
        if (timestamp == null || timestamp.isEmpty()) {
            throw new ParseObservationTimeException("The timestamp was not provided.");
        }

        try {
            Instant instant = Instant.ofEpochSecond(Long.parseLong(timestamp));
            return LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Tallinn")) ;
        } catch (Exception e) {
            throw new ParseObservationTimeException("Could not parse timestamp into into provided format.");
        }
    }
}
