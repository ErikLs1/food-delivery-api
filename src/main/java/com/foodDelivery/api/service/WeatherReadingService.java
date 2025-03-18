package com.foodDelivery.api.service;

import java.time.LocalDateTime;

public interface WeatherReadingService {
    void readWeatherData();
    LocalDateTime parseObservationTime(String timestamp);
}
