package com.omra.hotel.controller;

import com.omra.hotel.entity.Hotel;
import com.omra.hotel.repository.HotelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelRepository repo;

    public HotelController(HotelRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/search")
    public List<Hotel> search(@RequestParam(required = false) String city) {
        return repo.findByCityName(city);
    }
}
