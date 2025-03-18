package com.foodDelivery.api.repository;

import com.foodDelivery.api.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByWmoCode(String wmoCode);

    @Query("SELECT c.wmoCode FROM City c")
    List<String> findAllWmoCOdes();
}
