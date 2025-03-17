package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.CityDTO;
import com.foodDelivery.api.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDTO toDTO(City city);
    City toEntity(CityDTO cityDTO);
}
