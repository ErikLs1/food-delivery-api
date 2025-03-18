package com.foodDelivery.api.model;

import com.foodDelivery.api.model.enums.ConditionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents weather conditions that affect the delivery fee.
 */
@Entity
@Table(name = "CONDITIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conditions {

    /**
     * Unique identifier for the condition.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionsId;

    /**
     * The type of vehicle to which condition applies.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /**
     * The type of condition (e.g., PHENOMENON, TEMPERATURE, WIND)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ConditionType conditionType;

    /**
     * The minimum value for the condition (e.g., minimum temperature/wind for an extra fee).
     * Nullable for conditions with type PHENOMENON.
     */
    private Double minValue;

    /**
     * The maximum value for the condition (e.g., maximum temperature/wind for an extra fee).
     * Nullable for conditions with type PHENOMENON.
     */
    private Double maxValue;

    /**
     * The specific weather phenomenon that triggers this condition. (e.g., snow, thunder, rain)
     */
    @Column(length = 100)
    private String phenomenon;

    /**
     * The additional fee applied for the condition.
     */
    @Column(nullable = false)
    private Double conditionFee;

    /**
     * Indicated whether the vehicle is forbidden to use under this condition.
     */
    @Column(nullable = false)
    private Boolean usageForbidden;
}
