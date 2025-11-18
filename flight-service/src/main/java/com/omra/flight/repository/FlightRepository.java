package com.omra.flight.repository;

import com.omra.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
        SELECT f FROM Flight f
        WHERE LOWER(f.fromCity) = LOWER(:fromCity)
          AND LOWER(f.toCity) = LOWER(:toCity)
          AND DATE(f.departureTime) = :date
        """)
    List<Flight> search(@Param("fromCity") String fromCity,
                        @Param("toCity") String toCity,
                        @Param("date") LocalDate date);
}
