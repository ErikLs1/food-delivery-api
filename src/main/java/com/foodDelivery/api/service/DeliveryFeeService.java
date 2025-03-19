package com.foodDelivery.api.service;

import com.foodDelivery.api.model.Conditions;
import com.foodDelivery.api.model.WeatherData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing DeliveryFee-related business logic.
 *
 * <p>
 *     Service provides methods for retrieving the weather data, base fee information,
 *     condition processing, and delivery fee calculation method.
 * </p>
 */
public interface DeliveryFeeService {

    /**
     * Calculates the total delivery fee based on city, vehicle type and
     * observation time if it is provided by the controller.
     *
     * @param cityName the name of the city.
     * @param vehicleType the type of the vehicle (e.g., BIKE, CAR)
     * @param observationTime the observation time used in fee calculation;
     *                        if null then the latest available data is returned.
     * @return the calculated delivery fee.
     */
    Double calculateDeliveryFee(String cityName, String vehicleType, LocalDateTime observationTime);

    /**
     * Retrieves the latest weather data for the provided city.
     *
     * @param cityName the name of the city.
     * @return the most recent WeatherData, or null if no data is found.
     */
    WeatherData getLatestWeatherData(String cityName);

    /**
     * Retrieves the latest weather data for the provided city that has observation
     * time less than or equal to the provided dateTime.
     *
     * @param cityName the name of the city.
     * @param dateTime the upper bound of the observation time.
     * @return the suitable WeatherData, or null if no data was found.
     */
    WeatherData getLatestWeatherDataForTime(String cityName, LocalDateTime dateTime);

    /**
     *  Retrieves the vehicle fee for the specified city and vehicle type.
     *
     * @param cityName the name of the city.
     * @param vehicleType the type of the vehicle.
     * @return the vehicle fee value.
     */
    Double getBaseFee(String cityName, String vehicleType);

    /**
     * Processes a list of condition rules and finds the fee for the current weather condition.
     *
     * @param conditionsList a list of conditions.
     * @return the extra fee for the specific weather condition.
     */
    Double processConditions(List<Conditions> conditionsList);
}
