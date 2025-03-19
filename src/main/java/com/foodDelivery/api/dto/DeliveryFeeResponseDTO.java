package com.foodDelivery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Data Transfer Objects for Delivery Fee response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryFeeResponseDTO {

    /**
     * The total calculated fee.
     */
    private Double totalFee;
}
