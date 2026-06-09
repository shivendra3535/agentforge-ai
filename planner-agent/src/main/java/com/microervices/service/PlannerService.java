package com.microervices.service;


import com.microervices.llm.LlmService;
import org.springframework.stereotype.Service;

@Service
public class PlannerService {

    private final LlmService llmService;

    private static final String SYSTEM_PROMPT = """
            You are a software architect.
            Given a software requirement, return ONLY a valid JSON object
            with NO markdown, no explanation, no code fences.
            The JSON must have exactly these fields:
            {
              "components": ["list of major components"],
              "techStack": ["list of technologies"],
              "fileStructure": ["list of files to create"],
              "summary": "brief description of the plan"
            }
            """;

    public PlannerService(LlmService llmService) {
        this.llmService = llmService;
    }

    public String plan(String UserPrompt){
        return llmService.callLlm(SYSTEM_PROMPT, UserPrompt);
    }
}
