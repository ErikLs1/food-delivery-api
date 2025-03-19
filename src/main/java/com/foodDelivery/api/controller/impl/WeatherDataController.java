package com.foodDelivery.api.controller.impl;

import com.foodDelivery.api.controller.CRUDController;
import com.foodDelivery.api.dto.WeatherDataDTO;
import com.foodDelivery.api.service.WeatherDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller for managing WeatherData entities.
 *
 * <p>
 *     Provides endpoints to create, update, retrieve and delete WeatherData.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/weather-data")
public class WeatherDataController implements CRUDController<WeatherDataDTO, Long> {

    private final WeatherDataService weatherDataService;

    @Override
    public ResponseEntity<WeatherDataDTO> create(WeatherDataDTO dto) {
        WeatherDataDTO weatherDataDTO = weatherDataService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(weatherDataDTO);
    }

    @Override
    public ResponseEntity<WeatherDataDTO> update(Long id, WeatherDataDTO dto) {
        WeatherDataDTO weatherDataDTO = weatherDataService.update(id, dto);
        return ResponseEntity.ok(weatherDataDTO);
    }

    @Override
    public ResponseEntity<WeatherDataDTO> getById(Long id) {
        WeatherDataDTO weatherDataDTO = weatherDataService.getById(id);
        return ResponseEntity.ok(weatherDataDTO);
    }

    @Override
    public ResponseEntity<List<WeatherDataDTO>> getAll() {
        List<WeatherDataDTO> weatherDataDTOS = weatherDataService.getAll();
        return ResponseEntity.ok(weatherDataDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        weatherDataService.delete(id);
        return ResponseEntity.ok("WeatherData with id " + id + " was deleted!");
    }
}
