package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.BaseFeeDTO;
import com.foodDelivery.api.model.BaseFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BaseFeeMapper {
    BaseFeeMapper INSTANCE = Mappers.getMapper(BaseFeeMapper.class);

    @Mapping(source = "city.cityId", target = "cityId")
    @Mapping(source = "vehicle.vehicleId", target = "vehicleId")
    BaseFeeDTO toDTO(BaseFee baseFee);

    @Mapping(source = "cityId", target = "city.cityId")
    @Mapping(source = "vehicleId", target = "vehicle.vehicleId")
    BaseFee toEntity(BaseFeeDTO baseFeeDTO);
}
