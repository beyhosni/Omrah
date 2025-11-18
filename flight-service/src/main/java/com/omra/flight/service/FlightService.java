package com.omra.flight.service;

import com.omra.flight.entity.Flight;
import com.omra.flight.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository repo;

    public FlightService(FlightRepository repo) {
        this.repo = repo;
    }

    public List<Flight> search(String from, String to, LocalDate date) {
        return repo.search(from, to, date);
    }
}
