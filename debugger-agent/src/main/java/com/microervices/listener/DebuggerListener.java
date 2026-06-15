package com.microervices.listener;

import com.microervices.dto.AgentTask;
import com.microervices.service.DebuggerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DebuggerListener {

    private final DebuggerService debuggerService;
    private final KafkaTemplate<String, AgentTask> kafkaTemplate;

    public DebuggerListener(DebuggerService debuggerService, KafkaTemplate<String, AgentTask> kafkaTemplate) {
        this.debuggerService = debuggerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "agent-debugger-topic", groupId = "debugger-group")
    public void consume(AgentTask task){
        String debugResult= debuggerService.debug(task.getPreviousAgentOutput());
        AgentTask result= new AgentTask(
                task.getJobId(),
                task.getUserPrompt(),
                debugResult,
                "DEBUGGER"
        );
        kafkaTemplate.send("agent-result-topic",result);
    }
}
