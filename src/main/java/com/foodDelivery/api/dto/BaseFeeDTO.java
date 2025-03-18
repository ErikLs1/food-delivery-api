package com.foodDelivery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseFeeDTO {
    private Long baseFeeId;
    private Long cityId;
    private Long vehicleId;
    private Double vehicleFee;
}
