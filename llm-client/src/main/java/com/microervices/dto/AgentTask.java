package com.microervices.dto;

import java.io.Serializable;

public class AgentTask implements Serializable {
    private Long jobId;
    private String userPrompt;
    private String previousAgentOutput;
    private String sourceAgent;

    public AgentTask() {}

    public AgentTask(Long jobId, String userPrompt,
                     String previousAgentOutput, String sourceAgent) {
        this.jobId = jobId;
        this.userPrompt = userPrompt;
        this.previousAgentOutput = previousAgentOutput;
        this.sourceAgent = sourceAgent;
    }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getUserPrompt() { return userPrompt; }
    public void setUserPrompt(String userPrompt) { this.userPrompt = userPrompt; }

    public String getPreviousAgentOutput() { return previousAgentOutput; }
    public void setPreviousAgentOutput(String previousAgentOutput) {
        this.previousAgentOutput = previousAgentOutput;
    }

    public String getSourceAgent() { return sourceAgent; }
    public void setSourceAgent(String sourceAgent) { this.sourceAgent = sourceAgent; }
}