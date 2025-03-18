package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseFeeRepository extends JpaRepository<BaseFee, Long> {
    Optional<BaseFee> findByCity_CityNameAndVehicle_VehicleType(String city_cityName, VehicleType vehicleType);
}
