package com.foodDelivery.api.controller.impl;

import com.foodDelivery.api.controller.CRUDController;
import com.foodDelivery.api.dto.CityDTO;
import com.foodDelivery.api.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller for managing City entities.
 *
 * <p>
 *     Provides endpoints to create, update, retrieve and delete City.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/city")
public class CityController implements CRUDController<CityDTO, Long> {

    private final CityService cityService;

    @Override
    public ResponseEntity<CityDTO> create(CityDTO dto) {
        CityDTO cityDTO = cityService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityDTO);
    }

    @Override
    public ResponseEntity<CityDTO> update(Long id, CityDTO dto) {
        CityDTO cityDTO = cityService.update(id, dto);
        return ResponseEntity.ok(cityDTO);
    }

    @Override
    public ResponseEntity<CityDTO> getById(Long id) {
        CityDTO cityDTO = cityService.getById(id);
        return ResponseEntity.ok(cityDTO);
    }

    @Override
    public ResponseEntity<List<CityDTO>> getAll() {
        List<CityDTO> cityDTOS = cityService.getAll();
        return ResponseEntity.ok(cityDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        cityService.delete(id);
        return ResponseEntity.ok("City with id " + id + " was deleted!");
    }
}
