package com.microervices.dto;

public class DebugRequest {
    private Long jobId;
    private String codeJson;

    public DebugRequest() {}

    public DebugRequest(Long jobId, String codeJson) {
        this.jobId = jobId;
        this.codeJson = codeJson;
    }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getCodeJson() { return codeJson; }
    public void setCodeJson(String codeJson) {
        this.codeJson = codeJson;
    }
}
