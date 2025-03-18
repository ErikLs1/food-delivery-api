package com.foodDelivery.api.model;

import com.foodDelivery.api.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a type of vehicle used for food delivery.
 */
@Entity
@Table(name = "VEHICLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    /**
     * Unique identifier for the vehicle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    /**
     * The type of vehicle (e.g., CAR, BIKE).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private VehicleType vehicleType;

    /**
     * List of conditions that apply to this vehicle type.
     */
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Conditions> conditions;

    /**
     * List of fees applicable to this vehicle type.
     */
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<BaseFee> baseFees;
}
