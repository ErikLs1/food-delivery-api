package com.foodDelivery.api.dto;

import com.foodDelivery.api.model.enums.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Data Transfer Objects for Conditions.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionsDTO {

    /**
     * Unique identifier for the condition.
     */
    private Long conditionsId;

    /**
     * Identifier of the associated vehicle.
     */
    private Long vehicleId;

    /**
     * The type of condition (e.g., TEMPERATURE, WIND).
     */
    private ConditionType conditionType;

    /**
     * The minimum value for the condition (e.g., minimum temperature/wind for an extra fee).
     */
    private Double minValue;

    /**
     * The maximum value for the condition (e.g., maximum temperature/wind for an extra fee).
     */
    private Double maxValue;

    /**
     * The description of the phenomenon.
     */
    private String phenomenon;

    /**
     * The fee of the condition.
     */
    private Double conditionFee;

    /**
     * Indicates whether the vehicle usage is forbidden.
     */
    private Boolean usageForbidden;
}
