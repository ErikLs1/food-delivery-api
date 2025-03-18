package com.foodDelivery.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a city where food delivery is available.
 */
@Entity
@Table(name = "CITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    /**
     * Unique identifier for the city.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    /**
     * Name of the city.
     */
    @Column(nullable = false, length = 100)
    private String cityName;

    /**
     * The station name used for weather data retrieving.
     */
    @Column(nullable = false, length = 100)
    private String stationName;

    /**
     * The World Meteorological Organization (WMO) code for the weather station.
     */
    @Column(nullable = false, length = 50)
    private String wmoCode;

    /**
     * List of weather data related to the city.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<WeatherData> weatherData;

    /**
     * List of fees applicable to this city.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<BaseFee> baseFees;
}
