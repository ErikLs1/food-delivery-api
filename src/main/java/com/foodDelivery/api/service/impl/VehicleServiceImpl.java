package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.VehicleDTO;
import com.foodDelivery.api.exception.VehicleNotFoundException;
import com.foodDelivery.api.mapper.VehicleMapper;
import com.foodDelivery.api.model.Vehicle;
import com.foodDelivery.api.repository.VehicleRepository;
import com.foodDelivery.api.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleDTO create(VehicleDTO dto) {
        Vehicle vehicle = vehicleMapper.toEntity(dto);
        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(saved);
    }

    @Override
    public VehicleDTO update(Long id, VehicleDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle with id " + id + " was not found."));

        vehicle.setVehicleType(dto.getVehicleType());

        Vehicle updated = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(updated);
    }

    @Override
    public VehicleDTO getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle with id " + id + " was not found."));
        return vehicleMapper.toDTO(vehicle);
    }

    @Override
    public List<VehicleDTO> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle with id " + id + " was not found.");
        }
        vehicleRepository.deleteById(id);
    }
}
