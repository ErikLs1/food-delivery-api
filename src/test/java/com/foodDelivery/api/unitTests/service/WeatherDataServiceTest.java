package com.foodDelivery.api.unitTests.service;

import com.foodDelivery.api.dto.WeatherDataDTO;
import com.foodDelivery.api.exception.WeatherDataNotFoundException;
import com.foodDelivery.api.mapper.WeatherDataMapper;
import com.foodDelivery.api.model.WeatherData;
import com.foodDelivery.api.repository.WeatherDataRepository;
import com.foodDelivery.api.service.impl.WeatherDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherDataServiceTest {

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private WeatherDataMapper weatherDataMapper;

    @InjectMocks
    private WeatherDataServiceImpl weatherDataService;

    private WeatherData weatherData;
    private WeatherDataDTO weatherDataDTO;
    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        dateTime = LocalDateTime.now();
        weatherData = new WeatherData();
        weatherData.setWeatherDataId(1L);
        weatherData.setAirTemperature(20.0);
        weatherData.setWindSpeed(5.5);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setObservationTime(dateTime);

        weatherDataDTO = new WeatherDataDTO();
        weatherDataDTO.setWeatherDataId(1L);
        weatherDataDTO.setAirTemperature(20.0);
        weatherDataDTO.setWindSpeed(5.5);
        weatherDataDTO.setWeatherPhenomenon("Clear");
        weatherDataDTO.setObservationTime(dateTime);
    }

    @Test
    void testCreatWeatherData() {
        when(weatherDataMapper.toEntity(weatherDataDTO)).thenReturn(weatherData);
        when(weatherDataRepository.save(weatherData)).thenReturn(weatherData);
        when(weatherDataMapper.toDTO(weatherData)).thenReturn(weatherDataDTO);

        WeatherDataDTO result = weatherDataService.create(weatherDataDTO);

        assertNotNull(result);
        assertNotNull(result);
        assertEquals(20.0, result.getAirTemperature());
        assertEquals(5.5, result.getWindSpeed());
        assertEquals("Clear", result.getWeatherPhenomenon());
        verify(weatherDataRepository, times(1)).save(weatherData);
    }

    @Test
    void testUpdateWeatherData() {
        when(weatherDataRepository.findById(1L)).thenReturn(Optional.of(weatherData));
        when(weatherDataRepository.save(any(WeatherData.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(weatherDataMapper.toDTO(any(WeatherData.class))).thenAnswer(invocation -> {
            WeatherData weatherData1 = invocation.getArgument(0);
            WeatherDataDTO dto = new WeatherDataDTO();
            dto.setWeatherDataId(weatherData1.getWeatherDataId());
            dto.setAirTemperature(weatherData1.getAirTemperature());
            dto.setWindSpeed(weatherData1.getWindSpeed());
            dto.setWeatherPhenomenon(weatherData1.getWeatherPhenomenon());
            dto.setObservationTime(weatherData1.getObservationTime());
            return dto;
        });

        LocalDateTime newObservationTime = dateTime.plusHours(1);
        WeatherDataDTO updatedDTO = new WeatherDataDTO();
        updatedDTO.setAirTemperature(25.0);
        updatedDTO.setWindSpeed(10.0);
        updatedDTO.setWeatherPhenomenon("Rain shower");
        updatedDTO.setObservationTime(newObservationTime);

        weatherData.setAirTemperature(25.0);
        weatherData.setWindSpeed(10.0);
        weatherData.setWeatherPhenomenon("Rainy");
        weatherData.setObservationTime(newObservationTime);

        WeatherDataDTO result = weatherDataService.update(1L, updatedDTO);

        assertNotNull(result);
        assertEquals(25.0, result.getAirTemperature());
        assertEquals(10.0, result.getWindSpeed());
        assertEquals("Rain shower", result.getWeatherPhenomenon());
        assertEquals(newObservationTime, result.getObservationTime());
        verify(weatherDataRepository, times(1)).findById(1L);
        verify(weatherDataRepository, times(1)).save(weatherData);
    }

    @Test
    void testUpdateWeatherDataNotFound() {
        when(weatherDataRepository.findById(1L)).thenReturn(Optional.empty());
        WeatherDataDTO updatedDTO = new WeatherDataDTO();
        updatedDTO.setAirTemperature(25.0);
        updatedDTO.setWindSpeed(10.0);
        updatedDTO.setWeatherPhenomenon("Rainy");
        updatedDTO.setObservationTime(dateTime.plusHours(1));

        assertThrows(WeatherDataNotFoundException.class, () -> weatherDataService.update(1L, updatedDTO));
        verify(weatherDataRepository, times(1)).findById(1L);
        verify(weatherDataRepository, never()).save(any(WeatherData.class));
    }

    @Test
    void testGeWeatherDataById() {
        when(weatherDataRepository.findById(1L)).thenReturn(Optional.of(weatherData));
        when(weatherDataMapper.toDTO(weatherData)).thenReturn(weatherDataDTO);

        WeatherDataDTO result = weatherDataService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getWeatherDataId());
        assertEquals(20.0, result.getAirTemperature());
        verify(weatherDataRepository, times(1)).findById(1L);
    }

    @Test
    void testGetWeatherDataByIdWasNotFound() {
        when(weatherDataRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(WeatherDataNotFoundException.class, () -> weatherDataService.getById(1L));
        verify(weatherDataRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllWeatherData() {
        WeatherData weatherData2 = new WeatherData();
        weatherData2.setWeatherDataId(2L);
        weatherData2.setAirTemperature(18.0);
        weatherData2.setWindSpeed(3.5);
        weatherData2.setWeatherPhenomenon("Cloudy");
        weatherData2.setObservationTime(dateTime.plusHours(2));

        WeatherDataDTO weatherDataDTO2 = new WeatherDataDTO();
        weatherDataDTO2.setWeatherDataId(2L);
        weatherDataDTO2.setAirTemperature(18.0);
        weatherDataDTO2.setWindSpeed(3.5);
        weatherDataDTO2.setWeatherPhenomenon("Cloudy");
        weatherDataDTO2.setObservationTime(dateTime.plusHours(2));

        List<WeatherData> weatherDataList = List.of(weatherData, weatherData2);
        when(weatherDataRepository.findAll()).thenReturn(weatherDataList);
        when(weatherDataMapper.toDTO(weatherData)).thenReturn(weatherDataDTO);
        when(weatherDataMapper.toDTO(weatherData2)).thenReturn(weatherDataDTO2);

        List<WeatherDataDTO> result = weatherDataService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2L, result.get(1).getWeatherDataId());
        assertEquals("Cloudy", result.get(1).getWeatherPhenomenon());
        verify(weatherDataRepository, times(1)).findAll();
        verify(weatherDataMapper, times(2)).toDTO(any(WeatherData.class));
    }

    @Test
    void testDeleteWeatherData() {
        when(weatherDataRepository.existsById(1L)).thenReturn(true);
        doNothing().when(weatherDataRepository).deleteById(1L);

        assertDoesNotThrow(() -> weatherDataService.delete(1L));
        verify(weatherDataRepository, times(1)).existsById(1L);
        verify(weatherDataRepository, times(1)).deleteById(1L);

    }

    @Test
    void testDeleteWeatherDataNotFound() {
        when(weatherDataRepository.existsById(1L)).thenReturn(false);
        assertThrows(WeatherDataNotFoundException.class, () -> weatherDataService.delete(1L));
        verify(weatherDataRepository, times(1)).existsById(1L);
        verify(weatherDataRepository, never()).deleteById(30L);
    }
}
