package com.microervices.services;

import com.microervices.dto.AgentTask;
import com.microervices.models.AgentResult;
import com.microervices.models.WorkflowJob;
import com.microervices.repository.AgentResultRepository;
import com.microervices.repository.WorkflowJobRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorkflowService {

    private final WorkflowJobRepository jobRepository;
    private final AgentResultRepository resultRepository;
    private final KafkaTemplate<String, AgentTask> kafkaTemplate;

    public WorkflowService(WorkflowJobRepository jobRepository,
                           AgentResultRepository resultRepository,
                           KafkaTemplate<String, AgentTask> kafkaTemplate) {
        this.jobRepository = jobRepository;
        this.resultRepository = resultRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Called from WorkflowController — returns instantly (202)
    public Long createJob(String prompt) {
        WorkflowJob job = new WorkflowJob();
        job.setUserPrompt(prompt);
        job.setStatus(WorkflowJob.JobStatus.PENDING);
        jobRepository.save(job);

        updateJobStatus(job.getId(), WorkflowJob.JobStatus.RUNNING);

        AgentTask task = new AgentTask(job.getId(), prompt, null, "ORCHESTRATOR");
        kafkaTemplate.send("agent-planner-topic", task);

        return job.getId();
    }

    // Listens for ALL agent results
    @KafkaListener(topics = "agent-result-topic", groupId = "orchestrator-group",
            containerFactory = "agentTaskKafkaListenerContainerFactory")
    public void handleAgentResult(AgentTask result) {
        saveResult(result.getJobId(), result.getSourceAgent(), result.getPreviousAgentOutput());

        AgentTask nextTask = new AgentTask(
                result.getJobId(),
                result.getUserPrompt(),
                result.getPreviousAgentOutput(),
                result.getSourceAgent()
        );

        switch (result.getSourceAgent()) {
            case "PLANNER"    -> kafkaTemplate.send("agent-coder-topic",     nextTask);
            case "CODER"      -> kafkaTemplate.send("agent-debugger-topic",  nextTask);
            case "DEBUGGER"   -> updateJobStatus(result.getJobId(), WorkflowJob.JobStatus.DONE);
            default ->  updateJobStatus(result.getJobId(), WorkflowJob.JobStatus.FAILED);
        }
    }

    private void saveResult(Long jobId, String agentName, String output) {
        AgentResult result = new AgentResult();
        result.setJobId(jobId);
        result.setAgentName(agentName);
        result.setOutputText(output);
        result.setStatus("COMPLETED");
        result.setCreatedAt(LocalDateTime.now());
        resultRepository.save(result);
    }

    private void updateJobStatus(Long jobId, WorkflowJob.JobStatus status) {
        WorkflowJob job = jobRepository.findById(jobId).orElseThrow();
        job.setStatus(status);
        if (status == WorkflowJob.JobStatus.DONE || status == WorkflowJob.JobStatus.FAILED) {
            job.setCompletedAt(LocalDateTime.now());
        }
        jobRepository.save(job);
    }

    public WorkflowJob getJob(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }
}