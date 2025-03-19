package com.foodDelivery.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The Data Transfer Objects for Delivery Fee request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryFeeRequestDTO {

    /**
     * Then name of the city for which the delivery fee is required.
     */
    @NotBlank
    private String cityName;

    /**
     * The type of vehicle used for the delivery (e.g., BIKE, CAR)
     */
    @NotBlank
    private String vehicleType;

    /**
     * The observation time used to fetch the specific weather data.
     */
    private LocalDateTime observationTime;
}
