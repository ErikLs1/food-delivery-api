package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface to perform CRUD operation on BaseFee entities.
 * <p>
 *     Provides additional method to retrieve base fee based on city and vehicle type.
 * </p>
 */
@Repository
public interface BaseFeeRepository extends JpaRepository<BaseFee, Long> {
    /**
     * Finds a BaseFee for a provided city name and vehicle type.
     *
     * @param cityName the name of the city.
     * @param vehicleType the type of the vehicle (e.g., CAR, BIKE).
     * @return an Optional containing the baseFee if fee exists, or empty otherwise.
     */
    Optional<BaseFee> findByCity_CityNameAndVehicle_VehicleType(String cityName, VehicleType vehicleType);
}
