package com.foodDelivery.api.dto;

import com.foodDelivery.api.model.City;
import com.foodDelivery.api.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseFeeDTO {
    private Long baseFeeId;
    private City city;
    private Vehicle vehicle;
    private Double vehicleFee;
}
