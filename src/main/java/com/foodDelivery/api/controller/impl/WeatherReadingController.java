package com.foodDelivery.api.controller.impl;

import com.foodDelivery.api.service.WeatherReadingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for triggering the weather data reading.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/read-weather")
public class WeatherReadingController {

    private final WeatherReadingService weatherReadingService;

    /**
     * This endpoint starts the weather data reading process that
     * is performed by WeatherReadingServiceImpl
     */
    @GetMapping
    private void readWeather() {
        weatherReadingService.readWeatherData();
    }
}
