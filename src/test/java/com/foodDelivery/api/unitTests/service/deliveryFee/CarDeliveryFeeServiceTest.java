package com.foodDelivery.api.unitTests.service.deliveryFee;

import com.foodDelivery.api.exception.WeatherDataNotFoundException;
import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.model.WeatherData;
import com.foodDelivery.api.model.enums.VehicleType;
import com.foodDelivery.api.repository.BaseFeeRepository;
import com.foodDelivery.api.repository.ConditionsRepository;
import com.foodDelivery.api.repository.WeatherDataRepository;
import com.foodDelivery.api.service.impl.DeliveryFeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarDeliveryFeeServiceTest {

    @Mock
    private BaseFeeRepository baseFeeRepository;

    @Mock
    private ConditionsRepository conditionsRepository;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private DeliveryFeeServiceImpl deliveryFeeService;

    private WeatherData weatherData;
    private BaseFee baseFee;
    private LocalDateTime observationTime;


    @BeforeEach
    void  setUp() {
        observationTime = LocalDateTime.now();

        weatherData = new WeatherData();
        weatherData.setAirTemperature(10.00);
        weatherData.setWindSpeed(5.0);
        weatherData.setWeatherPhenomenon("Clear");

        baseFee = new BaseFee();
        baseFee.setVehicleFee(4.00);
    }

    @ParameterizedTest
    @CsvSource({
            "Tallinn, 4.00",
            "Tartu, 3.50",
            "Pärnu, 3.00"
    })
    void testCalculateDeliveryFeeForScooter_TimeProvided(String cityName, Double expectedFee) {
        String vehicleType = "CAR";
        baseFee.setVehicleFee(expectedFee);

        when(weatherDataRepository.findTopByCityCityNameAndObservationTimeLessThanEqualOrderByObservationTimeDesc(
                eq(cityName), eq(observationTime))).thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.CAR)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.CAR), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.CAR), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.CAR), anyString()))
                .thenReturn(Collections.emptyList());

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType, observationTime);
        assertEquals(expectedFee, totalFee);
    }

    @ParameterizedTest
    @CsvSource({
            "Tallinn, 4.00",
            "Tartu, 3.50",
            "Pärnu, 3.00"
    })
    void testCalculateDeliveryFeeForScooter_TimeNotProvided(String cityName, Double expectedFee) {
        String vehicleType = "CAR";
        baseFee.setVehicleFee(expectedFee);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.CAR)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.CAR), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.CAR), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.CAR), anyString()))
                .thenReturn(Collections.emptyList());

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(expectedFee, totalFee);
    }

    @Test
    void testCalculateDeliveryFeeForCar_NoWeatherData_TimeNotProvided() {
        String cityName = "Tallinn";
        String vehicleType = "CAR";

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(
                eq(cityName))).thenReturn(null);

        assertThrows(WeatherDataNotFoundException.class, () -> deliveryFeeService.calculateDeliveryFee(cityName, vehicleType));
    }
}
