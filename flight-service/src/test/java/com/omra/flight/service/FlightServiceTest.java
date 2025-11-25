package com.omra.flight.service;

import com.omra.flight.entity.Flight;
import com.omra.flight.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    void setUp() {
        flight1 = new Flight(
                1L,
                "Airline 1",
                "Paris",
                "Jeddah",
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 1, 16, 0),
                new BigDecimal("500.00"),
                360,
                0
        );

        flight2 = new Flight(
                2L,
                "Airline 2",
                "Paris",
                "Jeddah",
                LocalDateTime.of(2024, 1, 1, 14, 0),
                LocalDateTime.of(2024, 1, 1, 20, 0),
                new BigDecimal("450.00"),
                360,
                1
        );
    }

    @Test
    void search_Success() {
        when(flightRepository.search(anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(Arrays.asList(flight1, flight2));

        List<Flight> result = flightService.search("Paris", "Jeddah", LocalDate.of(2024, 1, 1));

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(flightRepository).search("Paris", "Jeddah", LocalDate.of(2024, 1, 1));
    }

    @Test
    void search_EmptyResult() {
        when(flightRepository.search(anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(List.of());

        List<Flight> result = flightService.search("Paris", "Unknown", LocalDate.of(2024, 1, 1));

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(flightRepository).search("Paris", "Unknown", LocalDate.of(2024, 1, 1));
    }
}
