package com.omra.hotel.controller;

import com.omra.hotel.entity.City;
import com.omra.hotel.entity.Hotel;
import com.omra.hotel.repository.CityRepository;
import com.omra.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class HotelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    private City mekke;
    private Hotel hotel1;

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll();
        cityRepository.deleteAll();

        mekke = new City();
        mekke.setName("Mekke");
        mekke.setCountry("Saudi Arabia");
        cityRepository.save(mekke);

        hotel1 = new Hotel();
        hotel1.setName("Grand Hotel");
        hotel1.setCity(mekke);
        hotel1.setStars(5);
        hotel1.setRating(4.5);
        hotel1.setDistanceMeters(500);
        hotel1.setAddress("123 Main St");
        hotelRepository.save(hotel1);
    }

    @Test
    void searchHotels_Success() throws Exception {
        mockMvc.perform(get("/api/hotels/search")
                        .param("city", "Mekke"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Grand Hotel"));
    }

    @Test
    void searchHotels_NoCity() throws Exception {
        mockMvc.perform(get("/api/hotels/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
