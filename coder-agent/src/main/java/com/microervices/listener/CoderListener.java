package com.microervices.listener;


import com.microervices.dto.AgentTask;
import com.microervices.service.CoderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CoderListener {

    private final CoderService coderService;
    private final KafkaTemplate<String, AgentTask> kafkaTemplate;

    public CoderListener(CoderService coderService, KafkaTemplate<String, AgentTask> kafkaTemplate) {
        this.coderService = coderService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "agent-coder-topic", groupId = "coder-group")
    public void consume(AgentTask task){
        String codeResult = coderService.generateCode(
                task.getUserPrompt(), task.getPreviousAgentOutput());

        AgentTask result = new AgentTask(
                task.getJobId(),
                task.getUserPrompt(),
                codeResult,
                "CODER"
        );
        kafkaTemplate.send("agent-result-topic", result);
    }
}
