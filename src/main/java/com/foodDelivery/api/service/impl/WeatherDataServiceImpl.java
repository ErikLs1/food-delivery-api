package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.WeatherDataDTO;
import com.foodDelivery.api.exception.WeatherDataNotFoundException;
import com.foodDelivery.api.mapper.WeatherDataMapper;
import com.foodDelivery.api.model.WeatherData;
import com.foodDelivery.api.repository.WeatherDataRepository;
import com.foodDelivery.api.service.WeatherDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the WeatherData service interface.
 *
 * <p>
 *     Provides basic CRUD operations.
 * </p>
 */
@Service
@AllArgsConstructor
public class WeatherDataServiceImpl implements WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;
    private final WeatherDataMapper weatherDataMapper;

    @Override
    public WeatherDataDTO create(WeatherDataDTO dto) {
        WeatherData weatherData = weatherDataMapper.toEntity(dto);
        WeatherData saved = weatherDataRepository.save(weatherData);
        return weatherDataMapper.toDTO(saved);
    }

    @Override
    public WeatherDataDTO update(Long id, WeatherDataDTO dto) {
        WeatherData weatherData = weatherDataRepository.findById(id)
                .orElseThrow(() -> new WeatherDataNotFoundException("WeatherData with id " + id + " was not found"));

        weatherData.setAirTemperature(dto.getAirTemperature());
        weatherData.setWindSpeed(dto.getWindSpeed());
        weatherData.setWeatherPhenomenon(dto.getWeatherPhenomenon());
        weatherData.setObservationTime(dto.getObservationTime());

        WeatherData updated = weatherDataRepository.save(weatherData);
        return weatherDataMapper.toDTO(updated);
    }

    @Override
    public WeatherDataDTO getById(Long id) {
        WeatherData weatherData = weatherDataRepository.findById(id)
                .orElseThrow(() -> new WeatherDataNotFoundException("WeatherData with id " + id + " was not found"));
        return weatherDataMapper.toDTO(weatherData);
    }

    @Override
    public List<WeatherDataDTO> getAll() {
        return weatherDataRepository.findAll()
                .stream()
                .map(weatherDataMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!weatherDataRepository.existsById(id)) {
            throw new WeatherDataNotFoundException("WeatherData with id " + id + " was not found");
        }
        weatherDataRepository.deleteById(id);
    }
}
