package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface to perform CRUD operation on City entities.
 *
 * <p>
 *     Provide methods to get City data by WMO code and fetch all WMO codes.
 * </p>
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Finds a City by its WMO code.
     *
     * @param wmoCode the WMO code.
     * @return an Optional containing the City if exists, or empty otherwise.
     */
    Optional<City> findByWmoCode(String wmoCode);

    /**
     * Retrieves all WMO codes of the stores stations.
     *
     * @return a list of all WMO codes.
     */
    @Query("SELECT c.wmoCode FROM City c")
    List<String> findAllWmoCodes();
}
