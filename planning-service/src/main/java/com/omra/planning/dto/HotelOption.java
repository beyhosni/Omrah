package com.omra.planning.dto;

import java.math.BigDecimal;

public record HotelOption(
        Long id,
        String name,
        String city,
        Integer stars,
        Integer distanceMeters,
        Double rating,
        BigDecimal pricePerNight
) {}
