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

/**
 * Unit tests with focus onf scooter delivery fee calculation.
 *
 * <p>
 *     It uses parameterized tests to check that the fee calculation is correct under different conditions
 *     as well as check that exceptions work as intended.
 * </p>
 */
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

    /**
     * Common data set up before each test.
     */
    @BeforeEach
    void setUp() {
        observationTime = LocalDateTime.now();

        weatherData = new WeatherData();
        weatherData.setAirTemperature(5.0);
        weatherData.setWindSpeed(3.0);
        weatherData.setWeatherPhenomenon("Clear");

        baseFee = new BaseFee();
    }

    /**
     * Test calculation of delivery for a scooter when observations time is provided.
     *
     * @param cityName the name of the city.
     * @param expectedFee the expected fee.
     */
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

    /**
     *  Test calculates delivery fee for scooter when observation time is not provided.
     *
     * @param cityName the city name.
     * @param expectedFee the fee associated with the city.
     */
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

    /**
     * Tests that exception is thrown when weather conditions forbid scooter usage.
     *
     * @param phenomenon the phenomenon description.
     */
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

    /**
     * Test calculates a delivery fee for a scooter when phenomenon is related to rain.
     *
     * @param phenomenon the phenomenon description.
     * @param expectedFee the fee for the provided phenomenon.
     */
    @ParameterizedTest
    @CsvSource({
            "Light shower, 0.50",
            "Moderate shower, 0.50",
            "Heavy shower, 0.50",
            "Light rain, 0.50",
            "Moderate rain, 0.50",
            "Heavy rain, 0.50",
    })
    void testCalculateDeliveryFeeForScooter_RainConditions(String phenomenon, Double expectedFee) {
        String cityName = "Tallinn";
        String vehicleType = "SCOOTER";
        baseFee.setVehicleFee(3.50);
        weatherData.setWeatherPhenomenon(phenomenon);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.SCOOTER)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(false);
        condition.setConditionFee(expectedFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.SCOOTER), eq(phenomenon)))
                .thenReturn(List.of(condition));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(3.50 + expectedFee, totalFee);
    }

    /**
     * Test calculates delivery fee for scooter when phenomenon is related to snow/sleet.
     *
     * @param phenomenon the weather phenomenon description.
     * @param expectedFee the fee for the provided phenomenon.
     */
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
    void testCalculateDeliveryFeeForScooter_SnowConditions(String phenomenon, Double expectedFee) {
        String cityName = "Tallinn";
        String vehicleType = "SCOOTER";
        baseFee.setVehicleFee(3.50);
        weatherData.setWeatherPhenomenon(phenomenon);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.SCOOTER)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(false);
        condition.setConditionFee(expectedFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.SCOOTER), eq(phenomenon)))
                .thenReturn(List.of(condition));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(3.50 + expectedFee, totalFee);
    }

    /**
     * Test calculates delivery fee for a scooter when air temperature is provided.
     *
     * @param airTemperature the air temperature.
     * @param expectedFee the expected fee for the temperature condition.
     */
    @ParameterizedTest
    @CsvSource({
            "-13.0, 1.00",
            "-10.5, 1.00",
            "-10.0, 0.50",
            "-5.0, 0.50",
            "1.0, 0.00"
    })
    void testCalculateDeliveryFeeForScooter_TemperatureConditions(Double airTemperature, Double expectedFee) {
        String cityName = "Tallinn";
        String vehicleType = "SCOOTER";
        baseFee.setVehicleFee(3.50);

        weatherData.setAirTemperature(airTemperature);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.SCOOTER)))
                .thenReturn(Optional.of(baseFee));
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());
        when(conditionsRepository.findWindConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions condition = new Conditions();
        condition.setUsageForbidden(false);
        condition.setConditionFee(expectedFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.SCOOTER), anyString()))
                .thenReturn(List.of(condition));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        assertEquals(3.50 + expectedFee, totalFee);
    }

    /**
     * Tests the delivery fee calculation when temperature and phenomenon are provided.
     *
     * @param airTemperature the air temperature.
     * @param phenomenon the weather phenomenon description.
     * @param expectedTempFee the expected fee for temperature condition.
     * @param expectedPhenomenonFee the expected fee phenomenon condition.
     */
    @ParameterizedTest
    @CsvSource({
            "-12.0, 'Light snow shower', 1.00, 1.00",
            "-6.0, 'Light rain', 0.50, 0.50",
            "5.0, 'Clear', 0.00, 0.00",
            "0.0, 'Light rain', 0.00, 0.50",
            "-7.0, 'Heavy rain', 0.50, 0.50",
            "-5.0, 'Heavy snow shower', 0.50, 1.00"
    })
    void testCalculateDeliveryFeeForScooter_TemperatureAndPhenomenon(Double airTemperature,
                                                                     String phenomenon,
                                                                     Double expectedTempFee,
                                                                     Double expectedPhenomenonFee) {
        String cityName = "Tallinn";
        String vehicleType = "SCOOTER";
        baseFee.setVehicleFee(3.50);

        weatherData.setAirTemperature(airTemperature);
        weatherData.setWeatherPhenomenon(phenomenon);

        when(weatherDataRepository.findTopByCityCityNameOrderByObservationTimeDesc(eq(cityName)))
                .thenReturn(weatherData);
        when(baseFeeRepository.findByCity_CityNameAndVehicle_VehicleType(eq(cityName), eq(VehicleType.SCOOTER)))
                .thenReturn(Optional.of(baseFee));

        Conditions condition2 = new Conditions();
        condition2.setUsageForbidden(false);
        condition2.setConditionFee(expectedTempFee);
        when(conditionsRepository.findTemperatureConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(List.of(condition2));
        when(conditionsRepository.findWindConditions(eq(VehicleType.SCOOTER), anyDouble()))
                .thenReturn(Collections.emptyList());

        Conditions condition1 = new Conditions();
        condition1.setUsageForbidden(false);
        condition1.setConditionFee(expectedPhenomenonFee);
        when(conditionsRepository.findPhenomenonConditions(eq(VehicleType.SCOOTER), anyString()))
                .thenReturn(List.of(condition1));

        Double totalFee = deliveryFeeService.calculateDeliveryFee(cityName, vehicleType);
        Double expectedTotalFee = baseFee.getVehicleFee() +expectedPhenomenonFee + expectedTempFee;
        assertEquals(expectedTotalFee, totalFee);
    }

    /**
     * Tests that {@link WeatherDataNotFoundException} is thrown when weather data was not found.
     */
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
