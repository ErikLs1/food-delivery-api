package com.foodDelivery.api.service;

import com.foodDelivery.api.model.Conditions;
import com.foodDelivery.api.model.WeatherData;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryFeeService {
    Double calculateDeliveryFee(String cityName, String vehicleType, LocalDateTime observationTime);
    WeatherData getLatestWeatherData(String cityName);
    WeatherData getLatestWeatherDataForTime(String cityName, LocalDateTime localDate);
    Double getBaseFee(String cityName, String vehicleType);
    Double processConditions(List<Conditions> conditionsList);
}
