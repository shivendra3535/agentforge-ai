package com.microervices.controller;

import com.microervices.dto.CreateJobRequest;
import com.microervices.services.WorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orchestrator/jobs")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(
            WorkflowService workflowService) {

        this.workflowService = workflowService;
    }

    @PostMapping
    public ResponseEntity<?> createJob(
            @RequestBody CreateJobRequest request){

        Long jobId = workflowService.createJob(request.getUserPrompt());
        return ResponseEntity.accepted()   // 202 Accepted — job is async now
                .body(Map.of("jobId", jobId));
    }
}