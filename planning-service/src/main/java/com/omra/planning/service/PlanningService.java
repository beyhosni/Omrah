package com.omra.planning.service;

import com.omra.planning.client.FlightClient;
import com.omra.planning.client.HotelClient;
import com.omra.planning.dto.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PlanningService {

    private final FlightClient flightClient;
    private final HotelClient hotelClient;

    public PlanningService(FlightClient flightClient, HotelClient hotelClient) {
        this.flightClient = flightClient;
        this.hotelClient = hotelClient;
    }

    public BestPlan computeOptimalPlan(PlanningRequest request) {
        int totalDays = (int) ChronoUnit.DAYS.between(request.dateFrom(), request.dateTo());
        if (totalDays <= 0) {
            throw new IllegalArgumentException("Dates invalides");
        }

        int mekkeDays = Math.max(3, (int) Math.ceil(0.6 * totalDays));
        int medineDays = totalDays - mekkeDays;
        if (medineDays < 2) {
            medineDays = 2;
            mekkeDays = totalDays - medineDays;
        }

        List<FlightOption> flights = flightClient.searchFlights(
                request.departureCity(),
                "Jeddah",
                request.dateFrom()
        );

        List<HotelOption> mekkeHotels = hotelClient.searchHotels("Mekke");
        List<HotelOption> medineHotels = hotelClient.searchHotels("Medine");

        BestPlan best = null;

        for (FlightOption f : flights) {
            for (HotelOption hM : mekkeHotels) {
                for (HotelOption hD : medineHotels) {

                    BigDecimal totalCost =
                            f.price().multiply(BigDecimal.valueOf(request.persons()))
                                    .add(hM.pricePerNight().multiply(BigDecimal.valueOf(mekkeDays * request.persons())))
                                    .add(hD.pricePerNight().multiply(BigDecimal.valueOf(medineDays * request.persons())));

                    if (totalCost.compareTo(request.budget()) > 0) continue;

                    double score = computeCombinationScore(f, hM, hD, totalCost, request.budget());

                    if (best == null || score > best.score()) {
                        best = new BestPlan(f, hM, hD, mekkeDays, medineDays, totalCost, score);
                    }
                }
            }
        }

        if (best == null) {
            throw new RuntimeException("Aucune combinaison ne respecte le budget");
        }

        return best;
    }

    private double computeCombinationScore(
            FlightOption f,
            HotelOption hM,
            HotelOption hD,
            BigDecimal cost,
            BigDecimal budget
    ) {
        double costRatio = cost.divide(budget, 4, RoundingMode.HALF_UP).doubleValue();
        double avgRating = (hM.rating() + hD.rating()) / 2.0;
        double distanceScore = 1.0 / (1 + (hM.distanceMeters() + hD.distanceMeters()) / 2000.0);

        return 0.5 * (1 - costRatio)
                + 0.3 * distanceScore
                + 0.2 * (avgRating / 5.0);
    }
}
