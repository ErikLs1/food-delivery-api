package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to perform CRUD operation on Vehicle entities.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
