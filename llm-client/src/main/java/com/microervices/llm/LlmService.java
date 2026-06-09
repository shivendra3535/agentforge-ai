package com.microervices.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LlmService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String OLLAMA_URL =
            "http://localhost:11434/api/chat";

    public LlmService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String callLlm(
            String systemPrompt,
            String userContent) {

        Map<String, Object> requestBody = Map.of(
                "model", "llama3.2",
                "stream", false,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", systemPrompt
                        ),
                        Map.of(
                                "role", "user",
                                "content", userContent
                        )
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(
                            OLLAMA_URL,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            JsonNode root =
                    objectMapper.readTree(
                            response.getBody()
                    );

            String raw = root
                    .path("message")
                    .path("content")
                    .asText();

            return cleanJson(raw);  // <-- only change

        } catch (Exception e) {
            throw new RuntimeException(
                    "LLM call failed: " + e.getMessage()
            );
        }
    }

    private String cleanJson(String raw) {
        if (raw == null) return "";

        String s = raw.trim();

        // Strip opening ```json or ```
        if (s.startsWith("```")) {
            int newline = s.indexOf('\n');
            if (newline != -1) {
                s = s.substring(newline + 1).trim();
            }
        }

        // Strip closing ```
        if (s.endsWith("```")) {
            s = s.substring(0, s.lastIndexOf("```")).trim();
        }

        // Extract only the JSON object, dropping any
        // explanation text the LLM adds before or after
        int start = s.indexOf('{');
        if (start == -1) return s;

        int depth = 0;
        int end = -1;
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == '{') depth++;
            else if (s.charAt(i) == '}') {
                depth--;
                if (depth == 0) {
                    end = i;
                    break;
                }
            }
        }

        if (end != -1) {
            return s.substring(start, end + 1);
        }

        return s;
    }
}