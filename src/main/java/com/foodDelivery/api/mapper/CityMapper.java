package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.CityDTO;
import com.foodDelivery.api.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting {@link City} entities and {@link CityDTO} objects.
 */
@Mapper(componentModel = "spring")
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    /**
     * Converts a {@link City} entity to {@link CityDTO}
     *
     * @param city the City entity.
     * @return the CityDTO.
     */
    CityDTO toDTO(City city);

    /**
     * Converts a {@link CityDTO} to a {@link City} entity.
     *
     * @param cityDTO the CityDTO.
     * @return the City entity.
     */
    City toEntity(CityDTO cityDTO);
}
