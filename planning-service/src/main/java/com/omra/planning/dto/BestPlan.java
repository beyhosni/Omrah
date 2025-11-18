package com.omra.planning.dto;

import java.math.BigDecimal;

public record BestPlan(
        FlightOption flight,
        HotelOption mekkeHotel,
        HotelOption medineHotel,
        int mekkeDays,
        int medineDays,
        BigDecimal totalCost,
        double score
) {}
