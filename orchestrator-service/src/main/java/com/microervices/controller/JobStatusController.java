package com.microervices.controller;

import com.microervices.dto.AgentResultResponse;
import com.microervices.dto.JobResponse;
import com.microervices.models.AgentResult;
import com.microervices.models.WorkflowJob;
import com.microervices.repository.AgentResultRepository;
import com.microervices.services.WorkflowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orchestrator/jobs")
public class JobStatusController {

    private final WorkflowService workflowService;
    private final AgentResultRepository resultRepository;

    public JobStatusController(
            WorkflowService workflowService,
            AgentResultRepository resultRepository) {

        this.workflowService = workflowService;
        this.resultRepository = resultRepository;
    }

    @GetMapping("/{id}")
    public JobResponse getJob(
            @PathVariable Long id){

        WorkflowJob job =
                workflowService.getJob(id);

        List<AgentResult> results =
                resultRepository.findByJobId(id);

        JobResponse response =
                new JobResponse();

        response.setId(job.getId());

        response.setPrompt(
                job.getUserPrompt()
        );

        response.setStatus(
                job.getStatus().name()
        );

        response.setCreatedAt(
                job.getCreatedAt()
        );

        response.setCompletedAt(
                job.getCompletedAt()
        );

        response.setResults(
                results.stream()
                        .map(r -> {

                            AgentResultResponse dto =
                                    new AgentResultResponse();

                            dto.setAgentName(
                                    r.getAgentName()
                            );

                            dto.setOutputText(
                                    r.getOutputText()
                            );

                            dto.setStatus(
                                    r.getStatus()
                            );

                            return dto;
                        })
                        .toList()
        );

        return response;
    }
}