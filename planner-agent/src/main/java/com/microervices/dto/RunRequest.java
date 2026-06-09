package com.microervices.dto;

public class RunRequest {
    private Long jobId;
    private String context; // previous agent's output

    public RunRequest() {}

    public RunRequest(Long jobId, String context) {
        this.jobId = jobId;
        this.context = context;
    }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
}
