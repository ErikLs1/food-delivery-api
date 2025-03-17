package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.ConditionsDTO;
import com.foodDelivery.api.model.Conditions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConditionsMapper {
    ConditionsMapper INSTANCE = Mappers.getMapper(ConditionsMapper.class);

    @Mapping(source = "vehicle.vehicleId", target = "vehicleId")
    ConditionsDTO toDTO(Conditions conditions);

    @Mapping(source = "vehicleId", target = "vehicle.vehicleId")
    Conditions toEntity(ConditionsDTO conditionsDTO);
}
