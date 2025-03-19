package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.City;
import com.foodDelivery.api.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface to perform CRUD operation on WeatherData entities.
 *
 * <p>
 *     Provides methods to retrieve the latest weather data for the provided city,
 *     and to get the weather data at the specific point in time.
 * </p>
 */
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * Finds the most recent WeatherData for a given city.
     *
     * @param cityName the name of the city.
     * @return the latest WeatherData.
     */
    WeatherData findTopByCityCityNameOrderByObservationTimeDesc(String cityName);

    /**
     * Finds the closest WeatherData to the give city where observation time
     * is less than or equal to the provided time.
     *
     * @param cityName the name of the city.
     * @param observationTime the upper bound of the observation time.
     * @return the WeatherData matching the conditions.
     */
    WeatherData findTopByCityCityNameAndObservationTimeLessThanEqualOrderByObservationTimeDesc(
            String cityName,
            LocalDateTime observationTime
    );

    List<WeatherData> city(City city);
}
