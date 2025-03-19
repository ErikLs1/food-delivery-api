package com.foodDelivery.api.mapper;

import com.foodDelivery.api.dto.BaseFeeDTO;
import com.foodDelivery.api.model.BaseFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting {@link BaseFee} entities and {@link BaseFeeDTO} objects.
 */
@Mapper(componentModel = "spring")
public interface BaseFeeMapper {
    BaseFeeMapper INSTANCE = Mappers.getMapper(BaseFeeMapper.class);

    /**
     * Converts a {@link BaseFee} entity to {@link BaseFeeDTO}
     *
     * @param baseFee the BaseFee entity.
     * @return the BaseFeeDTO
     */
    @Mapping(source = "city.cityId", target = "cityId")
    @Mapping(source = "vehicle.vehicleId", target = "vehicleId")
    BaseFeeDTO toDTO(BaseFee baseFee);

    /**
     * Converts a {@link BaseFeeDTO} to a {@link BaseFee} entity.
     *
     * @param baseFeeDTO the BaseFeeDTO.
     * @return the BaseFee entity.
     */
    @Mapping(source = "cityId", target = "city.cityId")
    @Mapping(source = "vehicleId", target = "vehicle.vehicleId")
    BaseFee toEntity(BaseFeeDTO baseFeeDTO);
}
