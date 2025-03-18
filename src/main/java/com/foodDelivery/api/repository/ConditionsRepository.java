package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.Conditions;
import com.foodDelivery.api.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface to perform CRUD operation on Conditions entities.
 *
 * <p>
 *    Provides additional methods to retrieve condition rules based on provided
 *    vehicle type and current weather condition.
 * </p>
 */
@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {

    /**
     * Retrieves all rules for a specific vehicle type that apply to a
     * current weather phenomenon.
     *
     * @param vehicleType the type of vehicle (e.g. CAR, BIKE).
     * @param phenomenon the weather phenomenon (e.g., Heave rain, Light snow).
     * @return a List of conditions that match the given values.
     */
    @Query("SELECT c FROM Conditions c WHERE c.vehicle.vehicleType = :vehicleType AND c.conditionType = 'PHENOMENON' AND c.phenomenon = :phenomenon")
    List<Conditions> findPhenomenonConditions(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("phenomenon") String phenomenon
    );

    /**
     * Retrieves temperature condition rules for a specific vehicle type
     * when temperature falls within a defined range.
     *
     * @param vehicleType the type of vehicle (e.g. CAR, BIKE).
     * @param temp the current air temperature.
     * @return a List of Conditions that match the given values.
     */
    @Query("SELECT c FROM Conditions c WHERE c.vehicle.vehicleType = :vehicleType AND c.conditionType = 'TEMPERATURE' AND :temp >= c.minValue AND :temp <= c.maxValue")
    List<Conditions> findTemperatureConditions(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("temp") Double temp
            );

    /**
     * Retrieves wind condition rules for a specific vehicle type
     * when the wind speed falls within defined range.
     *
     * @param vehicleType the type of vehicle (e.g. CAR, BIKE).
     * @param wind the current speed of the wind.
     * @return a List of conditions that match the given values.
     */
    @Query("SELECT c FROM Conditions c WHERE c.vehicle.vehicleType = :vehicleType AND c.conditionType = 'WIND' AND :wind >= c.minValue AND :wind <= c.maxValue")
    List<Conditions> findWindConditions(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("wind") Double wind
    );
}
