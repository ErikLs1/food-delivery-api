package com.foodDelivery.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the fee for food delivery based on city and vehicle type.
 */
@Entity
@Table(name = "BASE_FEE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseFee {

    /**
     * Unique identifier for the base fee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baseFeeId;

    /**
     * The city where the delivery fee applies.
     */
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    /**
     * The type of vehicle used for delivery
     */
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /**
     * The fee associated with the city and vehicle type.
     */
    @Column(nullable = false)
    private Double vehicleFee;
}
