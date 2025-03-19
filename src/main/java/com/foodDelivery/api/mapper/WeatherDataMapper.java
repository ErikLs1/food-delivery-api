package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.WeatherDataDTO;
import com.foodDelivery.api.model.WeatherData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting {@link WeatherData} entities and {@link WeatherDataDTO} objects.
 */
@Mapper(componentModel = "spring")
public interface WeatherDataMapper {
    WeatherDataMapper INSTANCE = Mappers.getMapper(WeatherDataMapper.class);

    /**
     * Converts a {@link WeatherData} entity to {@link WeatherDataDTO}
     *
     * @param weatherData the WeatherData entity.
     * @return the WeatherDataDTO.
     */
    @Mapping(source = "city.cityId", target = "cityId")
    WeatherDataDTO toDTO(WeatherData weatherData);

    /**
     * Converts a {@link WeatherDataDTO} to a {@link WeatherData} entity.
     *
     * @param weatherDataDTO the WeatherDataDTO.
     * @return the WeatherData entity.
     */
    @Mapping(source = "cityId", target = "city.cityId")
    WeatherData toEntity(WeatherDataDTO weatherDataDTO);
}
