package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.WeatherDataDTO;
import com.foodDelivery.api.model.WeatherData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WeatherDataMapper {
    WeatherDataMapper INSTANCE = Mappers.getMapper(WeatherDataMapper.class);

    @Mapping(source = "city.cityId", target = "cityId")
    WeatherDataDTO toDTO(WeatherData weatherData);

    @Mapping(source = "cityId", target = "city.cityId")
    WeatherData toEntity(WeatherDataDTO weatherDataDTO);
}
