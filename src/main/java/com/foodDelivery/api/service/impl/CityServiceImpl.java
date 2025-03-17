package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.CityDTO;
import com.foodDelivery.api.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Override
    public CityDTO create(CityDTO dto) {
        return null;
    }

    @Override
    public CityDTO update(Long aLong, CityDTO dto) {
        return null;
    }

    @Override
    public CityDTO getById(Long aLong) {
        return null;
    }

    @Override
    public List<CityDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long aLong) {

    }
}
