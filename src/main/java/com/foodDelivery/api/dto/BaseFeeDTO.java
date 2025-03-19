package com.foodDelivery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Data Transfer Objects for BaseFee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseFeeDTO {

    /**
     * Unique identifier for the base fee.
     */
    private Long baseFeeId;

    /**
     * Identifier of the city associated with the base fee.
     */
    private Long cityId;

    /**
     * Identifier of the vehicle associated with the base fee.
     */
    private Long vehicleId;

    /**
     * The vehicle fee value.
     */
    private Double vehicleFee;
}
