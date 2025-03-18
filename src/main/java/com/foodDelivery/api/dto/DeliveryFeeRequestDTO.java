package com.foodDelivery.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryFeeRequestDTO {

    @NotBlank
    private String cityName;

    @NotBlank
    private String vehicleType;

    private LocalDateTime observationTime;
}
