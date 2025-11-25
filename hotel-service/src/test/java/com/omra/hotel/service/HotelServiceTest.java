package com.omra.hotel.service;

import com.omra.hotel.entity.City;
import com.omra.hotel.entity.Hotel;
import com.omra.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel1;
    private Hotel hotel2;
    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Mekke");
        city.setCountry("Saudi Arabia");

        hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setName("Hotel 1");
        hotel1.setCity(city);
        hotel1.setStars(5);
        hotel1.setRating(4.5);

        hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setName("Hotel 2");
        hotel2.setCity(city);
        hotel2.setStars(4);
        hotel2.setRating(4.0);
    }

    @Test
    void searchHotels_Success() {
        when(hotelRepository.findByCityName(anyString())).thenReturn(Arrays.asList(hotel1, hotel2));

        List<Hotel> result = hotelService.searchHotels("Mekke");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(hotelRepository).findByCityName("Mekke");
    }

    @Test
    void searchHotels_EmptyResult() {
        when(hotelRepository.findByCityName(anyString())).thenReturn(List.of());

        List<Hotel> result = hotelService.searchHotels("Unknown City");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(hotelRepository).findByCityName("Unknown City");
    }
}
