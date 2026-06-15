package com.microervices.listener;


import com.microervices.dto.AgentTask;
import com.microervices.service.PlannerService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlannerListener {

    private final PlannerService plannerService;
    private final KafkaTemplate<String, AgentTask> kafkaTemplate;

    public PlannerListener(PlannerService plannerService,
                           KafkaTemplate<String, AgentTask> kafkaTemplate) {
        this.plannerService = plannerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "agent-planner-topic", groupId = "planner-group")
    public void consume(AgentTask task) {
        String planResult = plannerService.plan(task.getUserPrompt());

        AgentTask result = new AgentTask(
                task.getJobId(),
                task.getUserPrompt(),
                planResult,
                "PLANNER"
        );
        kafkaTemplate.send("agent-result-topic", result);
    }

}
