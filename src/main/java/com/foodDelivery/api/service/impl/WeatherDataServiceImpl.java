package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.WeatherDataDTO;
import com.foodDelivery.api.service.WeatherDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {
    @Override
    public WeatherDataDTO create(WeatherDataDTO dto) {
        return null;
    }

    @Override
    public WeatherDataDTO update(Long aLong, WeatherDataDTO dto) {
        return null;
    }

    @Override
    public WeatherDataDTO getById(Long aLong) {
        return null;
    }

    @Override
    public List<WeatherDataDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long aLong) {

    }
}
