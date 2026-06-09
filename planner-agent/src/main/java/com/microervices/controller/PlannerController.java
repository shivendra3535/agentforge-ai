package com.microervices.controller;

import com.microervices.dto.RunRequest;
import com.microervices.service.PlannerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/planner")
public class PlannerController {

    private final PlannerService plannerService;

    public PlannerController(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @PostMapping("/run")
    public String run(@RequestBody RunRequest request) {
        return plannerService.plan(request.getContext());
    }
}
