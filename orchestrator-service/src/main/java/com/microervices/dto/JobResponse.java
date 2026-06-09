package com.microervices.dto;

import java.time.LocalDateTime;
import java.util.List;

public class JobResponse {

    private Long id;

    private String prompt;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private List<AgentResultResponse> results;

    public JobResponse() {
    }

    public JobResponse(Long id, String prompt, String status, LocalDateTime createdAt, LocalDateTime completedAt, List<AgentResultResponse> results) {
        this.id = id;
        this.prompt = prompt;
        this.status = status;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.results = results;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
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

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public List<AgentResultResponse> getResults() {
        return results;
    }

    public void setResults(List<AgentResultResponse> results) {
        this.results = results;
    }
}