package com.microervices.service;

import com.microervices.llm.LlmService;
import org.springframework.stereotype.Service;

@Service
public class CoderService {
    private final LlmService llmService;

    private static final String SYSTEM_PROMPT = """
            You are a senior Java Spring Boot developer.
            Given an architecture plan and a user requirement,
            generate complete working Spring Boot code.
            Return ONLY valid JSON with NO markdown, no explanation, no code fences.
            Format:
            {
              "files": [
                {
                  "fileName": "UserController.java",
                  "packagePath": "com.example.controller",
                  "content": "package com.example.controller; ..."
                }
              ]
            }
            Use MySQL + Spring Data JPA. Include all annotations.
            Generate controller, service, repository, entity, and DTO classes.
            """;

    public CoderService(LlmService llmService) {
        this.llmService = llmService;
    }

    public String generateCode(String userPrompt, String planJson){
        String userMessage= """
                Original Requirement: %s
                Architecture plan: %s
                Generate the complete Spring Boot implementation.
                """.formatted(userPrompt,planJson);

        return llmService.callLlm(SYSTEM_PROMPT,userMessage);
    }
}
