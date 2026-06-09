package com.microervices.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agent_results",
        indexes = {
                @Index(
                        name = "idx_job_id",
                        columnList = "jobId"
                )
        }
        )
public class AgentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;

    private String agentName;

    @Column(columnDefinition = "TEXT")
    private String outputText;

    private String status;

    private LocalDateTime createdAt;

    public AgentResult(){
    }

    public AgentResult(Long jobId, String agentName, String outputText, String status, LocalDateTime createdAt) {
        this.jobId = jobId;
        this.agentName = agentName;
        this.outputText = outputText;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
