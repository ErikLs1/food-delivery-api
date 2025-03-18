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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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


    @Override
    @Scheduled(cron = "* 15 * * * *")
    public void readWeatherData() {
        try {
            String responseXml = restTemplate.getForObject(OBSERVATIONS_URL, String.class);
            Observations observations = xmlMapper.readValue(responseXml, Observations.class);
            LocalDateTime observationTime = parseObservationTime(observations.getTimestamp());

            List<String> wmoCodes = cityRepository.findAllWmoCOdes();

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
