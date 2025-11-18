package com.omra.planning.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PlanningRequest(
        String departureCity,
        LocalDate dateFrom,
        LocalDate dateTo,
        int persons,
        BigDecimal budget
) {}
