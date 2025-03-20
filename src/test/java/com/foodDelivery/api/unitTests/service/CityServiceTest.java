package com.foodDelivery.api.unitTests.service;

import com.foodDelivery.api.dto.CityDTO;
import com.foodDelivery.api.exception.CityNotFoundException;
import com.foodDelivery.api.mapper.CityMapper;
import com.foodDelivery.api.model.City;
import com.foodDelivery.api.repository.CityRepository;
import com.foodDelivery.api.service.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private CityServiceImpl cityService;

    private City city;
    private CityDTO cityDTO;

    @BeforeEach
    public void setUp() {
        city = new City();
        city.setCityId(1L);
        city.setCityName("Tallinn");
        city.setStationName("Tallinn-Harku");
        city.setWmoCode("26038");

        cityDTO = new CityDTO();
        cityDTO.setCityId(1L);
        cityDTO.setCityName("Tallinn");
        cityDTO.setStationName("Tallinn-Harku");
        cityDTO.setWmoCode("26038");
    }

    @Test
    void testCreatCity() {
        when(cityMapper.toEntity(cityDTO)).thenReturn(city);
        when(cityRepository.save(city)).thenReturn(city);
        when(cityMapper.toDTO(city)).thenReturn(cityDTO);

        CityDTO result = cityService.create(cityDTO);

        assertNotNull(result);
        assertEquals("Tallinn", result.getCityName());
        assertEquals("Tallinn-Harku", result.getStationName());
        assertEquals("26038", result.getWmoCode());
        verify(cityRepository, times(1)).save(city);
    }

    @Test
    void testUpdateCity() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(cityRepository.save(any(City.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cityMapper.toDTO(any(City.class))).thenAnswer(invocation -> {
            City updatedCity = invocation.getArgument(0);
            return new CityDTO(
                    updatedCity.getCityId(),
                    updatedCity.getCityName(),
                    updatedCity.getStationName(),
                    updatedCity.getWmoCode()
            );
        });

        CityDTO updateDTO = new CityDTO(1L, "TALLINN", "Tallinn-HARKU", "26038");
        city.setCityName(updateDTO.getCityName());
        city.setStationName(updateDTO.getStationName());

        CityDTO result = cityService.update(1L, updateDTO);

        assertNotNull(result);
        assertEquals("TALLINN", result.getCityName());
        assertEquals("Tallinn-HARKU", result.getStationName());
        verify(cityRepository, times(1)).save(city);
    }

    @Test
    void testUpdateCityNotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());
        CityDTO updateDTO = new CityDTO(1L, "TALLINN", "Tallinn-HARKU", "26038");

        assertThrows(CityNotFoundException.class, () -> cityService.update(1L, updateDTO));
        verify(cityRepository, times(1)).findById(1L);
        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    void testGetCityById() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(cityMapper.toDTO(city)).thenReturn(cityDTO);

        CityDTO result = cityService.getById(1L);

        assertNotNull(result);
        assertEquals("Tallinn", result.getCityName());
        assertEquals("Tallinn-Harku", result.getStationName());
        assertEquals("26038", result.getWmoCode());
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    void testCityGetByIdNotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> cityService.getById(1L));
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllCities() {
        City city1 = new City();
        city1.setCityId(2L);
        city1.setCityName("Tartu");
        city1.setStationName("Tartu-Something");
        city1.setWmoCode("2414");

        CityDTO cityDTO1 = new CityDTO();
        cityDTO1.setCityId(2L);
        cityDTO1.setCityName("Tartu");
        cityDTO1.setStationName("Tartu-Something");
        cityDTO1.setWmoCode("2414");

        List<City> cities = List.of(city, city1);
        when(cityRepository.findAll()).thenReturn(cities);
        when(cityMapper.toDTO(city)).thenReturn(cityDTO);
        when(cityMapper.toDTO(city1)).thenReturn(cityDTO1);

        List<CityDTO> result = cityService.getAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2L, result.get(1).getCityId());
        assertEquals("Tartu", result.get(1).getCityName());
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    void testDeleteCity() {
        when(cityRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cityRepository).deleteById(1L);

        assertDoesNotThrow(() -> cityService.delete(1L));
        verify(cityRepository, times(1)).existsById(1L);
        verify(cityRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCityNotFound() {
        when(cityRepository.existsById(1L)).thenReturn(false);

        assertThrows(CityNotFoundException.class, () -> cityService.delete(1L));
        verify(cityRepository, times(1)).existsById(1L);
        verify(cityRepository, never()).deleteById(30L);
    }

}
