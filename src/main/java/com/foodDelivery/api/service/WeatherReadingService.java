package com.foodDelivery.api.service;

import java.time.LocalDateTime;

/**
 * Service interface for managing weatherReading-related business logic.
 *
 * <p>
 *     The service is responsible for fetching and parsing the XML weather
 *     data form the API.
 * </p>
 */
public interface WeatherReadingService {

    /**
     * Reads weather data from the external API which is scheduler to run every 15 minutes.
     */
    void readWeatherData();

    /**
     * Parses the observation timestamp form Epoch time format to a LocalDateTime.
     *
     * @param timestamp the observation timestamp as string.
     * @return the LocalDateTime.
     */
    LocalDateTime parseObservationTime(String timestamp);
}
