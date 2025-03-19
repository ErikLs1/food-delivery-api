package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.ConditionsDTO;
import com.foodDelivery.api.model.Conditions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting {@link Conditions} entities and {@link ConditionsDTO} objects.
 */
@Mapper(componentModel = "spring")
public interface ConditionsMapper {
    ConditionsMapper INSTANCE = Mappers.getMapper(ConditionsMapper.class);

    /**
     * Converts a {@link Conditions} entity to {@link ConditionsDTO}
     *
     * @param conditions the Conditions Entity.
     * @return the ConditionsDTO.
     */
    @Mapping(source = "vehicle.vehicleId", target = "vehicleId")
    ConditionsDTO toDTO(Conditions conditions);

    /**
     * Converts a {@link ConditionsDTO} to a {@link Conditions} entity.
     *
     * @param conditionsDTO the ConditionsDTO.
     * @return the Conditions entity.
     */
    @Mapping(source = "vehicleId", target = "vehicle.vehicleId")
    Conditions toEntity(ConditionsDTO conditionsDTO);
}
