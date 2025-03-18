package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    WeatherData findTopByCityCityNameOrderByObservationTimeDesc(String cityName);

    WeatherData findTopByCityCityNameAndObservationTimeLessThanEqualOrderByObservationTimeDesc(
            String cityName,
            LocalDateTime observationTime
    );
}
