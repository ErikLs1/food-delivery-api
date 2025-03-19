package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.VehicleDTO;
import com.foodDelivery.api.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting {@link Vehicle} entities and {@link VehicleDTO} objects.
 */
@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    /**
     * Converts a {@link Vehicle} entity to {@link VehicleDTO}
     *
     * @param vehicle the Vehicle entity.
     * @return the VehicleDTO.
     */
    VehicleDTO toDTO(Vehicle vehicle);

    /**
     * Converts a {@link VehicleDTO} to a {@link Vehicle} entity.
     *
     * @param vehicleDTO the VehicleDTO.
     * @return the Vehicle entity.
     */
    Vehicle toEntity(VehicleDTO vehicleDTO);
}
