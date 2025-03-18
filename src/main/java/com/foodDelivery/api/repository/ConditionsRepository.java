package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.Conditions;
import com.foodDelivery.api.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {

    @Query("SELECT c FROM Conditions c WHERE c.vehicle.vehicleType = :vehicleType AND c.conditionType = 'PHENOMENON' AND c.phenomenon = :phenomenon")
    List<Conditions> findPhenomenonConditions(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("phenomenon") String phenomenon
    );

    @Query("SELECT c FROM Conditions c WHERE c.vehicle.vehicleType = :vehicleType AND c.conditionType = 'TEMPERATURE' AND :temp >= c.minValue AND :temp <= c.maxValue")
    List<Conditions> findTemperatureConditions(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("temp") Double temp
            );

    @Query("SELECT c FROM Conditions c WHERE c.vehicle.vehicleType = :vehicleType AND c.conditionType = 'WIND' AND :wind >= c.minValue AND :wind <= c.maxValue")
    List<Conditions> findWindConditions(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("wind") Double wind
    );
}
