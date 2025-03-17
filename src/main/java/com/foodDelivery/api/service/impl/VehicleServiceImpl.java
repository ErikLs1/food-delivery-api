package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.VehicleDTO;
import com.foodDelivery.api.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Override
    public VehicleDTO create(VehicleDTO dto) {
        return null;
    }

    @Override
    public VehicleDTO update(Long aLong, VehicleDTO dto) {
        return null;
    }

    @Override
    public VehicleDTO getById(Long aLong) {
        return null;
    }

    @Override
    public List<VehicleDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long aLong) {

    }
}
