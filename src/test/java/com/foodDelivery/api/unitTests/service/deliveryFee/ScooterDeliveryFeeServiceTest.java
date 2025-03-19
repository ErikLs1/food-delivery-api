package com.foodDelivery.api.unitTests.service.deliveryFee;

import com.foodDelivery.api.exception.WeatherDataNotFoundException;
import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.model.Conditions;
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
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScooterDeliveryFeeServiceTest {

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
    void setUp() {
        observationTime = LocalDateTime.now();

        weatherData = new WeatherData();
        weatherData.setAirTemperature(5.0);
        weatherData.setWindSpeed(3.0);
        weatherData.setWeatherPhenomenon("Clear");

        baseFee = new BaseFee();
    }

    @ParameterizedTest
    @CsvSource({
            "Tallinn, 3.50",
            "Tartu, 3.00",
            "Pärnu, 2.50"
    })
    void testCalculateDeliveryFeeForScooter_TimeProvided(String cityName, Double expectedFee) {
        String vehicleType = "SCOOTER";
        baseFee.setVehicleFee(expectedFee);

        when(weatherDataRepository.findTopByCityCityNameAndObservationTimeLessThanEqualOrderByObservationTimeDesc(
                eq(cityName), eq(observationTime))).thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.SCOOTER)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.SCOOTER), anyString()))
                .thenReturn(Collections.emptyList());

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType, observationTime);
        assertEquals(expectedFee, totalFee);
    }

    @ParameterizedTest
    @CsvSource({
            "Tallinn, 3.50",
            "Tartu, 3.00",
            "Pärnu, 2.50"
    })
    void testCalculateDeliveryFeeForScooter_TimeNotProvided(String cityName, Double expectedFee) {
        String vehicleType = "SCOOTER";
        baseFee.setVehicleFee(expectedFee);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.SCOOTER)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.SCOOTER), anyString()))
                .thenReturn(Collections.emptyList());

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(expectedFee, totalFee);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Glaze", "Hail", "Thunder"})
    void testCalculateDeliveryFeeForScooter_WhereUsageForbidden(String phenomenon) {
        String cityName = "Tallinn";
        String vehicleType = "SCOOTER";
        baseFee.setVehicleFee(3.50);

        weatherData.setWeatherPhenomenon(phenomenon);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.SCOOTER)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.SCOOTER), eq(weatherData.getAirTemperature())))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.SCOOTER), eq(weatherData.getWindSpeed())))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(true);
        condition.setConditionFee(0.00);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.SCOOTER), eq(phenomenon)))
                .thenReturn(List.of(condition));

        assertThrows(IllegalArgumentException.class, () ->
                deliveryFeeService.calculateDeliveryFee(cityName, vehicleType));
    }
    @Test
    void testCalculateDeliveryFeeForScooter_NoWeatherData() {
        String cityName = "Tallinn";
        String vehicleType = "SCOOTER";

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(null);

        assertThrows(WeatherDataNotFoundException.class, () ->
                deliveryFeeService.calculateDeliveryFee(cityName, vehicleType));
    }
}
