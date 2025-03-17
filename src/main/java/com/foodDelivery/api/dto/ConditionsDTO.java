package com.foodDelivery.api.dto;

import com.foodDelivery.api.model.enums.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionsDTO {
    private Long conditionsId;
    private Long vehicleId;
    private ConditionType conditionType;
    private Double minValue;
    private Double maxValue;
    private String phenomenon;
    private Double conditionFee;
    private Boolean usageForbidden;
}
