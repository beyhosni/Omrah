package com.omra.hotel.service;

import com.omra.hotel.entity.Hotel;
import com.omra.hotel.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private static final Logger logger = LoggerFactory.getLogger(HotelService.class);
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> searchHotels(String cityName) {
        logger.info("Searching hotels for city: {}", cityName);
        List<Hotel> hotels = hotelRepository.findByCityName(cityName);
        logger.info("Found {} hotels", hotels.size());
        return hotels;
    }
}
