package com.foodDelivery.api.controller.impl;

import com.foodDelivery.api.dto.DeliveryFeeRequestDTO;
import com.foodDelivery.api.dto.DeliveryFeeResponseDTO;
import com.foodDelivery.api.service.DeliveryFeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for calculating delivery fee.
 *
 * <p>
 *     Exposes endpoint to calculate the total delivery fee based on the provided
 *     city name, vehicle type, and observation time.
 * </p>
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/delivery-fee")
public class DeliveryFeeController {
    private final DeliveryFeeService deliveryFeeService;

    /**
     * Calculates the delivery fee.
     *
     * @param request the delivery fee request payload.
     * @return a ResponseEntity containing the DeliveryFeeResponseDTO.
     */
    @GetMapping
    private ResponseEntity<DeliveryFeeResponseDTO> getDeliveryFee(@RequestBody @Valid DeliveryFeeRequestDTO request) {
        Double totalFee = deliveryFeeService.calculateDeliveryFee(
                request.getCityName(),
                request.getVehicleType(),
                request.getObservationTime()
        );
        DeliveryFeeResponseDTO response = new DeliveryFeeResponseDTO(totalFee);
        return ResponseEntity.ok(response);
    }
}
