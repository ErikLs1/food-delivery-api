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
public class BikeDeliveryFeeServiceTest {
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
        weatherData.setAirTemperature(10.00);
        weatherData.setWindSpeed(5.0);
        weatherData.setWeatherPhenomenon("Clear");

        baseFee = new BaseFee();
        baseFee.setVehicleFee(3.00);
    }

    @ParameterizedTest
    @CsvSource({
            "Tallinn, 3.00",
            "Tartu, 2.50",
            "Pärnu, 2.00"
    })
    void testCalculateDeliveryFeeForBike_TimeProvided(String cityName, Double expectedFee) {
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(expectedFee);

        when(weatherDataRepository.findTopByCityCityNameAndObservationTimeLessThanEqualOrderByObservationTimeDesc(
                eq(cityName), eq(observationTime))).thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), anyString()))
                .thenReturn(Collections.emptyList());

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType, observationTime);
        assertEquals(expectedFee, totalFee);
    }

    @ParameterizedTest
    @CsvSource({
            "Tallinn, 3.00",
            "Tartu, 2.50",
            "Pärnu, 2.00"
    })
    void testCalculateDeliveryFeeForBike_TimeNotProvided(String cityName, Double expectedFee) {
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(expectedFee);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), anyString()))
                .thenReturn(Collections.emptyList());

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(expectedFee, totalFee);
    }

    @ParameterizedTest
    @CsvSource({
            "15.0, 0.5",
            "5.0, 0.00"
    })
    void testCalculateDeliveryFeeForBike_WindConditions(Double windSpeed, Double expectedWindFee) {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(3.00);
        weatherData.setWindSpeed(windSpeed);
        weatherData.setWeatherPhenomenon("Clear");

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), anyString()))
                .thenReturn(Collections.emptyList());

        Conditions windCondition = new Conditions();
        windCondition.setUsageForbidden(false);
        windCondition.setConditionFee(expectedWindFee);
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), eq(windSpeed)))
                    .thenReturn(List.of(windCondition));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(3.00 + expectedWindFee, totalFee);

    }

    @Test
    void testCalculateDeliveryFeeForBike_WindTooStrong() {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";
        Double windSpeed = 25.0;
        weatherData.setWindSpeed(windSpeed);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions windCondition = new Conditions();
        windCondition.setUsageForbidden(true);
        windCondition.setConditionFee(0.00);
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), eq(windSpeed)))
                .thenReturn(List.of(windCondition));

        assertThrows(IllegalArgumentException.class,
                () -> deliveryFeeService.calculateDeliveryFee(cityName, vehicleType));
    }

    @ParameterizedTest
    @CsvSource({
            "Light shower, 0.50",
            "Moderate shower, 0.50",
            "Heavy shower, 0.50",
            "Light rain, 0.50",
            "Moderate rain, 0.50",
            "Heavy rain, 0.50",
    })
    void testCalculateDeliveryFeeForBike_RainConditions(String phenomenon, Double expectedFee) {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(3.00);
        weatherData.setWeatherPhenomenon(phenomenon);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(false);
        condition.setConditionFee(expectedFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), eq(phenomenon)))
                .thenReturn(List.of(condition));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(3.00 + expectedFee, totalFee);
    }

    @ParameterizedTest
    @CsvSource({
            "Light snow shower, 1.00",
            "Moderate snow shower, 1.00",
            "Heavy snow shower, 1.00",
            "Light sleet, 1.00",
            "Moderate sleet, 1.00",
            "Light snowfall, 1.00",
            "Moderate snowfall, 1.00",
            "Heavy snowfall, 1.00",
    })
    void testCalculateDeliveryFeeForBike_SnowConditions(String phenomenon, Double expectedFee) {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(3.00);
        weatherData.setWeatherPhenomenon(phenomenon);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(false);
        condition.setConditionFee(expectedFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), eq(phenomenon)))
                .thenReturn(List.of(condition));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(3.00 + expectedFee, totalFee);
    }

    // Air temperature and fee
    @ParameterizedTest
    @CsvSource({
            "-13.0, 1.00",
            "-10.5, 1.00",
            "-10.0, 0.50",
            "-5.0, 0.50",
            "1.0, 0.00"
    })
    void testCalculateDeliveryFeeForBike_TemperatureConditions(Double airTemperature, Double expectedFee) {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(3.00);

        weatherData.setAirTemperature(airTemperature);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(false);
        condition.setConditionFee(expectedFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), anyString()))
                .thenReturn(List.of(condition));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(3.00 + expectedFee, totalFee);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Glaze", "Hail", "Thunder"})
    void testCalculateDeliveryFeeForScooter_WhereUsageForbidden(String phenomenon) {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(3.00);

        weatherData.setWeatherPhenomenon(phenomenon);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));

        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), eq(weatherData.getAirTemperature())))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), eq(weatherData.getWindSpeed())))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(true);
        condition.setConditionFee(0.00);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), eq(phenomenon)))
                .thenReturn(List.of(condition));

        assertThrows(IllegalArgumentException.class, () ->
                deliveryFeeService.calculateDeliveryFee(cityName, vehicleType));
    }


    @ParameterizedTest
    @CsvSource({
            // airTemp, phenomenon, windSpeed, expectedTemFee, expectedPhenomenonFee, expectedWindFee
            "-12.0, 'Light snow shower', 12.0, 1.00, 1.00, 0.50",
            "-6.0, 'Light rain', 8.0, 0.50, 0.50, 0.00",
            "5.0, 'Clear', 9.0, 0.00, 0.00, 0.00",
            "0.0, 'Light rain', 11.0, 0.00, 0.50, 0.50",
            "-7.0, 'Heavy rain', 19.0, 0.50, 0.50, 0.50",
            "-5.0, 'Heavy snow shower', 9.0, 0.50, 1.00, 0.00"
    })
    void testCalculateDeliveryFeeForScooter_TemperatureAndPhenomenon(Double airTemperature,
                                                                     String phenomenon,
                                                                     Double windSpeed,
                                                                     Double expectedTempFee,
                                                                     Double expectedPhenomenonFee,
                                                                     Double expectedWindFee) {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";
        baseFee.setVehicleFee(3.00);

        weatherData.setAirTemperature(airTemperature);
        weatherData.setWeatherPhenomenon(phenomenon);
        weatherData.setWindSpeed(windSpeed);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.BIKE)))
                .thenReturn(Optional.of(baseFee));

        Conditions condition2 = new Conditions();
        condition2.setUsageForbidden(false);
        condition2.setConditionFee(expectedTempFee);
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.BIKE), anyDouble()))
                .thenReturn(List.of(condition2));

        Conditions condition3 = new Conditions();
        condition3.setUsageForbidden(false);
        condition3.setConditionFee(expectedWindFee);
        when(conditionsRepository.findWindConditions(eq(VehicleType.BIKE), eq(windSpeed)))
                .thenReturn(List.of(condition3));

        Conditions condition1 = new Conditions();
        condition1.setUsageForbidden(false);
        condition1.setConditionFee(expectedPhenomenonFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.BIKE), eq(phenomenon)))
                .thenReturn(List.of(condition1));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        Double expectedTotalFee = baseFee.getVehicleFee() +expectedPhenomenonFee + expectedWindFee + expectedTempFee;
        assertEquals(expectedTotalFee, totalFee);
    }

    @Test
    void testCalculateDeliveryFeeForBike_NoWeatherData() {
        String cityName = "Tallinn";
        String vehicleType = "BIKE";

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(null);

        assertThrows(WeatherDataNotFoundException.class, () ->
                deliveryFeeService.calculateDeliveryFee(cityName, vehicleType));
    }

}
