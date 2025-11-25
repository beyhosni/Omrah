package com.omra.flight.controller;

import com.omra.flight.entity.Flight;
import com.omra.flight.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FlightControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();

        Flight flight = new Flight(
                null,
                "Air France",
                "Paris",
                "Jeddah",
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 1, 16, 0),
                new BigDecimal("500.00"),
                360,
                0
        );
        flightRepository.save(flight);
    }

    @Test
    void searchFlights_Success() throws Exception {
        mockMvc.perform(get("/api/flights/search")
                        .param("from", "Paris")
                        .param("to", "Jeddah")
                        .param("date", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].airline").value("Air France"));
    }

    @Test
    void searchFlights_NoResults() throws Exception {
        mockMvc.perform(get("/api/flights/search")
                        .param("from", "London")
                        .param("to", "Jeddah")
                        .param("date", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
