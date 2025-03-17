package com.foodDelivery.api.model;

import com.foodDelivery.api.model.enums.ConditionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONDITIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionsId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ConditionType conditionType;

    private Double minValue;

    private Double maxValue;

    @Column(length = 100)
    private String phenomenon;

    @Column(nullable = false)
    private Double conditionFee;

    @Column(nullable = false)
    private Boolean usageForbidden;
}
