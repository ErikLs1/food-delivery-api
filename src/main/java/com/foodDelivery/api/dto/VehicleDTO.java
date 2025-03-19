package com.foodDelivery.api.dto;

import com.foodDelivery.api.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Data Transfer Objects for Delivery Fee vehicle.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    /**
     * Unique identifier for the vehicle.
     */
    private Long vehicleId;

    /**
     * The type of the vehicle (e.g., CAR, BIKE).
     */
    private VehicleType vehicleType;
}
