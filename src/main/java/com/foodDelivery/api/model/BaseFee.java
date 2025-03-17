package com.foodDelivery.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BASE_FEE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baseFeeId;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private Double vehicleFee;
}
