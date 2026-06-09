package com.microervices.dto;

public class CoderRequest {
    private Long jobId;
    private String userPrompt;
    private String planJson;

    public CoderRequest() {}

    public CoderRequest(Long jobId, String userPrompt, String planJson) {
        this.jobId = jobId;
        this.userPrompt = userPrompt;
        this.planJson = planJson;
    }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getUserPrompt() { return userPrompt; }
    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public String getPlanJson() { return planJson; }
    public void setPlanJson(String planJson) {
        this.planJson = planJson;
    }
}
