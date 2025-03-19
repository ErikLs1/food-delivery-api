package com.foodDelivery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Data Transfer Objects for City.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    /**
     * Unique identifier for the city.
     */
    private Long cityId;

    /**
     * The name of the city.
     */
    private String cityName;

    /**
     * The name of the weather station.
     */
    private String stationName;

    /**
     * The World Meteorological Organization (WMO) code of the station.
     */
    private String wmoCode;
}
