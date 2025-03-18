package com.foodDelivery.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents weather data collected for a specific city.
 */
@Entity
@Table(name = "WEATHER_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData {

    /**
     * Unique identifier for the collected weather data.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherDataId;

    /**
     * The city to which this weather data belongs.
     */
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    /**
     * The air temperature at the specific observation time.
     */
    @Column(nullable = false)
    private Double airTemperature;

    /**
     * The wind speed at the specific observation time.
     */
    @Column(nullable = false)
    private Double windSpeed;

    /**
     * The weather phenomenon at the specific observation time.
     */
    @Column(nullable = false)
    private String weatherPhenomenon;

    /**
     * The time when weather data was collected.
     */
    @Column(nullable = false)
    private LocalDateTime observationTime;
}
