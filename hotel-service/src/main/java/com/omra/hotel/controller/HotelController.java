package com.omra.hotel.controller;

import com.omra.hotel.entity.Hotel;
import com.omra.hotel.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/search")
    public List<Hotel> search(@RequestParam(required = false) String city) {
        return hotelService.searchHotels(city);
    }
}
