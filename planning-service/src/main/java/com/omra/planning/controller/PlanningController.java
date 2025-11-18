package com.omra.planning.controller;

import com.omra.planning.dto.BestPlan;
import com.omra.planning.dto.PlanningRequest;
import com.omra.planning.service.PlanningService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    private final PlanningService service;

    public PlanningController(PlanningService service) {
        this.service = service;
    }

    @PostMapping("/optimal")
    public BestPlan compute(@RequestBody PlanningRequest request) {
        return service.computeOptimalPlan(request);
    }
}
