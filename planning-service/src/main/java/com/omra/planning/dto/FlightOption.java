package com.omra.planning.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightOption(
        Long id,
        String airline,
        String fromCity,
        String toCity,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        BigDecimal price,
        Integer durationMinutes,
        Integer stopsCount
) {}
