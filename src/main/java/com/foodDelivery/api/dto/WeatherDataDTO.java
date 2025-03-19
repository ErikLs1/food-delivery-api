package com.foodDelivery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The Data Transfer Objects for WeatherData.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataDTO {

    /**
     * Unique identifier for the weather data.
     */
    private Long weatherDataId;

    /**
     * Identifier of the city associated with the weather data.
     */
    private Long cityId;

    /**
     * The recorded air temperature.
     */
    private Double airTemperature;

    /**
     * The recorded wind speed.
     */
    private Double windSpeed;

    /**
     * Description of the weather phenomenon (e.g., Clear, Light Rain).
     */
    private String weatherPhenomenon;

    /**
     * The time of the recorder observation.
     */
    private LocalDateTime observationTime;
}
