package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.exception.BaseFeeNotFoundException;
import com.foodDelivery.api.exception.WeatherDataNotFoundException;
import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.model.Conditions;
import com.foodDelivery.api.model.WeatherData;
import com.foodDelivery.api.model.enums.VehicleType;
import com.foodDelivery.api.repository.BaseFeeRepository;
import com.foodDelivery.api.repository.ConditionsRepository;
import com.foodDelivery.api.repository.WeatherDataRepository;
import com.foodDelivery.api.service.DeliveryFeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the DeliveryFeeService interface.
 *
 * <p>
 *     The service implements business logic for calculating the total
 *     delivery fee for the given city, vehicle type and observation time.
 * </p>
 */
@Service
@AllArgsConstructor
public class DeliveryFeeServiceImpl implements DeliveryFeeService {


    private final BaseFeeRepository baseFeeRepository;
    private final ConditionsRepository conditionsRepository;
    private final WeatherDataRepository weatherDataRepository;


    /**
     * Calculate the delivery fee based on the provided parameters.
     *
     * <p>
     *     It retrieves the latest weather data for the give city or by observation time if it is provided.
     *     Retrieves the base fee according to the city and vehicle used.
     *     Calculates extra fee based on temperature, wind and weather conditions.
     * </p>
     *
     * @param cityName the name of the city.
     * @param vehicleType the type of the vehicle (e.g., BIKE, CAR)
     * @param observationTime the observation time used in fee calculation;
     *                        if null then the latest available data is returned.
     * @return the total delivery fee.
     */
    @Override
    public Double calculateDeliveryFee(String cityName, String vehicleType, LocalDateTime observationTime) {
        WeatherData weatherData = observationTime != null ?
                getLatestWeatherDataForTime(cityName, observationTime) :
                getLatestWeatherData(cityName);

        if (weatherData == null) {
            throw new WeatherDataNotFoundException("Could not get weather data for this city");
        }

        VehicleType vehType = VehicleType.valueOf(vehicleType.toUpperCase());
        Double deliveryFee = getBaseFee(cityName, vehicleType);
        Double extraFee = 0.00;

        // Add temperature fee
        if (weatherData.getAirTemperature() != null) {
            Double temp = weatherData.getAirTemperature();
            List<Conditions> temperatureCon = conditionsRepository.findTemperatureConditions(vehType, temp);
            extraFee += processConditions(temperatureCon);
        }

        // Add wind fee
        if (weatherData.getWindSpeed() != null) {
            Double wind = weatherData.getWindSpeed();
            List<Conditions> windCon = conditionsRepository.findWindConditions(vehType, wind);
            extraFee += processConditions(windCon);
        }

        // Add phenomenon fee
        if (weatherData.getWeatherPhenomenon() != null) {
            String phenomenon = weatherData.getWeatherPhenomenon();
            List<Conditions> phenomenonCon = conditionsRepository.findPhenomenonConditions(vehType, phenomenon);
            extraFee += processConditions(phenomenonCon);
        }

        return deliveryFee + extraFee;
     }

    /**
     * Retrieves the latest WeatherData for the given city.
     *
     * @param cityName the name of the city.
     * @return the most recent WeatherData, or null if none was found.
     */
    @Override
    public WeatherData getLatestWeatherData(String cityName) {
       return weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(cityName);
    }

    /**
     * Retrieves the latest WeatherData for the given city that has observation time less
     * than or equal to the provided dateTime.
     *
     * @param cityName the name of the city.
     * @param dateTime the upper bound of the observation time.
     * @return matching WeatherData, or null if none was found.
     */
    @Override
    public WeatherData getLatestWeatherDataForTime(String cityName, LocalDateTime dateTime) {
        return weatherDataRepository.findTopByCityCityNameAndObservationTimeLessThanEqualOrderByObservationTimeDesc(
                cityName,
                dateTime
        );
    }

    /**
     * Retrieves the abe fee for the provided city and vehicle type.
     *
     * @param cityName the name of the city.
     * @param vehicleType the type of the vehicle.
     * @return the base fee value.
     */
    @Override
    public Double getBaseFee(String cityName, String vehicleType) {
        VehicleType vehType = VehicleType.valueOf(vehicleType.toUpperCase());
        Optional<BaseFee> baseFee = baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(cityName, vehType);
        if (baseFee.isEmpty()) {
            throw new BaseFeeNotFoundException("Could not find the fee for city " + cityName + " and vehicle " + vehicleType);
        }

        return baseFee.get().getVehicleFee() != null ? baseFee.get().getVehicleFee() : 0.00;

    }

    /**
     * Processes a list of Conditions to find the suitable fee.
     *
     * <p>
     *     If usage of the vehicle is forbidden due to weather conditions the exception is thrown.
     * </p>
     *
     * @param conditionsList a list of conditions.
     * @return the extra fee.
     */
    @Override
    public Double processConditions(List<Conditions> conditionsList) {
        Double sum = 0.00;
        for(Conditions con : conditionsList) {
            if (Boolean.TRUE.equals(con.getUsageForbidden())) {
                throw new IllegalArgumentException("Usage of selected vehicle is not allowed due to weather conditions.");
            }

            if (con.getConditionFee() != null) {
                sum += con.getConditionFee();
            }
        }

        return sum;
    }
}
