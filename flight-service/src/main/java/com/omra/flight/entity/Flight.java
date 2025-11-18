package com.omra.flight.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
public record Flight(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    
    String airline,
    String fromCity,
    String toCity,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime,
    BigDecimal price,
    Integer durationMinutes,
    Integer stopsCount
) {
    // Constructeur par défaut requis par JPA
    public Flight() {
        this(null, null, null, null, null, null, null, null, null);
    }
}
