package com.foodDelivery.api.controller.impl;

import com.foodDelivery.api.controller.CRUDController;
import com.foodDelivery.api.dto.VehicleDTO;
import com.foodDelivery.api.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller for managing Vehicle entities.
 *
 * <p>
 *     Provides endpoints to create, update, retrieve and delete Vehicle.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController implements CRUDController<VehicleDTO, Long> {

    private final VehicleService vehicleService;

    @Override
    public ResponseEntity<VehicleDTO> create(VehicleDTO dto) {
        VehicleDTO vehicleDTO = vehicleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleDTO);
    }

    @Override
    public ResponseEntity<VehicleDTO> update(Long id, VehicleDTO dto) {
        VehicleDTO vehicleDTO = vehicleService.update(id, dto);
        return ResponseEntity.ok(vehicleDTO);
    }

    @Override
    public ResponseEntity<VehicleDTO> getById(Long id) {
        VehicleDTO vehicleDTO = vehicleService.getById(id);
        return ResponseEntity.ok(vehicleDTO);
    }

    @Override
    public ResponseEntity<List<VehicleDTO>> getAll() {
        List<VehicleDTO> vehicleDTOS = vehicleService.getAll();
        return ResponseEntity.ok(vehicleDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        vehicleService.delete(id);
        return ResponseEntity.ok("Vehicle with id " + id + " was deleted!");
    }
}
