package com.microervices.services;

import com.microervices.dto.CoderRequest;
import com.microervices.dto.DebugRequest;
import com.microervices.models.AgentResult;
import com.microervices.models.WorkflowJob;
import com.microervices.repository.AgentResultRepository;
import com.microervices.repository.WorkflowJobRepository;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

@Service
public class WorkflowService {

    private final WorkflowJobRepository jobRepository;
    private final AgentResultRepository resultRepository;
    private final RestTemplate restTemplate;

    private static final String PLANNER_URL =
            "http://localhost:8082/internal/planner/run";

    private static final String CODER_URL =
            "http://localhost:8083/internal/coder/run";

    private static final String DEBUGGER_URL =
            "http://localhost:8084/internal/debugger/run";

    public WorkflowService(
            WorkflowJobRepository jobRepository,
            AgentResultRepository resultRepository,
            RestTemplate restTemplate) {

        this.jobRepository = jobRepository;
        this.resultRepository = resultRepository;
        this.restTemplate = restTemplate;
    }

    public Long createJob(String prompt) {
        WorkflowJob job = new WorkflowJob();
        job.setUserPrompt(prompt);
        job.setStatus(WorkflowJob.JobStatus.PENDING);
        jobRepository.save(job);

        runWorkflow(job.getId(), prompt);

        return job.getId();
    }

    @Async
    public void runWorkflow(Long jobId, String userPrompt) {

        try {
            updateJobStatus(jobId, WorkflowJob.JobStatus.RUNNING);

            var plannerBody = new java.util.HashMap<String, Object>();
            plannerBody.put("jobId", jobId);
            plannerBody.put("context", userPrompt);

            String planResult = postToAgent(PLANNER_URL, plannerBody);
            saveResult(jobId, "PLANNER", planResult);

            CoderRequest coderRequest =
                    new CoderRequest(jobId, userPrompt, planResult);

            String codeResult = postToAgent(CODER_URL, coderRequest);
            saveResult(jobId, "CODER", codeResult);

            DebugRequest debugRequest =
                    new DebugRequest(jobId, codeResult);

            String debugResult = postToAgent(DEBUGGER_URL, debugRequest);
            saveResult(jobId, "DEBUGGER", debugResult);

            updateJobStatus(jobId, WorkflowJob.JobStatus.DONE);

        } catch (Exception e) {
            updateJobStatus(jobId, WorkflowJob.JobStatus.FAILED);
            saveResult(jobId, "ERROR", e.getMessage());
        }
    }

    private String postToAgent(String url, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        return response.getBody();
    }

    private void saveResult(
            Long jobId,
            String agentName,
            String output) {

        AgentResult result = new AgentResult();
        result.setJobId(jobId);
        result.setAgentName(agentName);
        result.setOutputText(output);
        result.setStatus("COMPLETED");
        result.setCreatedAt(LocalDateTime.now());
        resultRepository.save(result);
    }

    private void updateJobStatus(
            Long jobId,
            WorkflowJob.JobStatus status) {

        WorkflowJob job = jobRepository.findById(jobId)
                .orElseThrow();
        job.setStatus(status);

        if (status == WorkflowJob.JobStatus.DONE
                || status == WorkflowJob.JobStatus.FAILED) {
            job.setCompletedAt(LocalDateTime.now());
        }

        jobRepository.save(job);
    }

    public WorkflowJob getJob(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );
    }
}