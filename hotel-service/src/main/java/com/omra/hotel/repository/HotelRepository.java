package com.omra.hotel.repository;

import com.omra.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("""
           SELECT h FROM Hotel h
           WHERE (:cityName IS NULL OR LOWER(h.city.name) = LOWER(:cityName))
           """)
    List<Hotel> findByCityName(@Param("cityName") String cityName);
}
