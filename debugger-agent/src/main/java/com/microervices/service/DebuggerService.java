package com.microervices.service;

import com.microervices.llm.LlmService;
import org.springframework.stereotype.Service;

@Service
public class DebuggerService {

    private final LlmService llmService;

    private static final String SYSTEM_PROMPT = """
            You are a senior code reviewer and debugger.
            You will receive Java Spring Boot code in JSON format.
            Review it for: missing annotations, wrong imports,
            logic errors, security issues, and null pointer risks.
            Return the FIXED code in the SAME JSON format:
            {
              "files": [
                {
                  "fileName": "...",
                  "packagePath": "...",
                  "content": "..."
                }
              ]
            }
            Return ONLY valid JSON. No markdown, no explanation.
            """;

    public DebuggerService(LlmService llmService) {
        this.llmService = llmService;
    }

    public String debug(String codeJson) {
        return llmService.callLlm(
                SYSTEM_PROMPT,
                "Review and fix this code: " + codeJson
        );
    }
}
