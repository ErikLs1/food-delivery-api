package com.foodDelivery.api.service.impl;

import com.foodDelivery.api.dto.CityDTO;
import com.foodDelivery.api.exception.CityNotFoundException;
import com.foodDelivery.api.mapper.CityMapper;
import com.foodDelivery.api.model.City;
import com.foodDelivery.api.repository.CityRepository;
import com.foodDelivery.api.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the City service interface.
 *
 * <p>
 *     Provides basic CRUD operations.
 * </p>
 */
@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public CityDTO create(CityDTO dto) {
        City city = cityMapper.toEntity(dto);
        City saved = cityRepository.save(city);
        return cityMapper.toDTO(saved);
    }

    @Override
    public CityDTO update(Long id, CityDTO dto) {
       City city = cityRepository.findById(id).
               orElseThrow(() -> new CityNotFoundException("City with id " + id + " was not found."));

       city.setCityName(dto.getCityName());
       city.setStationName(dto.getStationName());
       city.setWmoCode(dto.getWmoCode());

       City updated = cityRepository.save(city);
       return cityMapper.toDTO(updated);
    }

    @Override
    public CityDTO getById(Long id) {
        City city = cityRepository.findById(id).
                orElseThrow(() -> new CityNotFoundException("City with id " + id + " was not found."));
        return cityMapper.toDTO(city);
    }

    @Override
    public List<CityDTO> getAll() {
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new CityNotFoundException("City with id " + id + " was not found.");
        }
        cityRepository.deleteById(id);
    }
}
