package com.omra.planning.service;

import com.omra.planning.client.FlightClient;
import com.omra.planning.client.HotelClient;
import com.omra.planning.dto.*;
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
class PlanningServiceTest {

    @Mock
    private FlightClient flightClient;

    @Mock
    private HotelClient hotelClient;

    @InjectMocks
    private PlanningService planningService;

    private PlanningRequest planningRequest;
    private List<FlightOption> flights;
    private List<HotelOption> mekkeHotels;
    private List<HotelOption> medineHotels;

    @BeforeEach
    void setUp() {
        planningRequest = new PlanningRequest(
                "Paris",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 15),
                2,
                new BigDecimal("5000.00")
        );

        flights = Arrays.asList(
                new FlightOption(1L, "Airline 1", "Paris", "Jeddah",
                        LocalDateTime.of(2024, 1, 1, 10, 0),
                        LocalDateTime.of(2024, 1, 1, 16, 0),
                        new BigDecimal("500.00"), 360, 0)
        );

        mekkeHotels = Arrays.asList(
                new HotelOption(1L, "Mekke Hotel", "Mekke", 5, 500, 4.5, new BigDecimal("100.00"))
        );

        medineHotels = Arrays.asList(
                new HotelOption(2L, "Medine Hotel", "Medine", 4, 800, 4.0, new BigDecimal("80.00"))
        );
    }

    @Test
    void computeOptimalPlan_Success() {
        when(flightClient.searchFlights(anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(flights);
        when(hotelClient.searchHotels("Mekke")).thenReturn(mekkeHotels);
        when(hotelClient.searchHotels("Medine")).thenReturn(medineHotels);

        BestPlan result = planningService.computeOptimalPlan(planningRequest);

        assertNotNull(result);
        assertNotNull(result.flight());
        assertNotNull(result.mekkeHotel());
        assertNotNull(result.medineHotel());
        assertTrue(result.totalCost().compareTo(planningRequest.budget()) <= 0);
        verify(flightClient).searchFlights("Paris", "Jeddah", LocalDate.of(2024, 1, 1));
        verify(hotelClient).searchHotels("Mekke");
        verify(hotelClient).searchHotels("Medine");
    }

    @Test
    void computeOptimalPlan_InvalidDates() {
        PlanningRequest invalidRequest = new PlanningRequest(
                "Paris",
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 1, 1),
                2,
                new BigDecimal("5000.00")
        );

        assertThrows(IllegalArgumentException.class, () -> planningService.computeOptimalPlan(invalidRequest));
    }

    @Test
    void computeOptimalPlan_BudgetTooLow() {
        PlanningRequest lowBudgetRequest = new PlanningRequest(
                "Paris",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 15),
                2,
                new BigDecimal("100.00")
        );

        when(flightClient.searchFlights(anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(flights);
        when(hotelClient.searchHotels("Mekke")).thenReturn(mekkeHotels);
        when(hotelClient.searchHotels("Medine")).thenReturn(medineHotels);

        assertThrows(RuntimeException.class, () -> planningService.computeOptimalPlan(lowBudgetRequest));
    }
}
