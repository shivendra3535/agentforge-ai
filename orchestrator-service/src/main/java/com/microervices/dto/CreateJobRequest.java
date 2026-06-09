package com.microervices.dto;

public class CreateJobRequest {
    private String userPrompt;
    public CreateJobRequest(){}
    public CreateJobRequest(String userPrompt){
        this.userPrompt=userPrompt;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }
}
