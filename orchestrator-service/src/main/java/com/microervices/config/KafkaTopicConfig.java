package com.microervices.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic plannerTopic(){
        return TopicBuilder.name("agent-planner-topic").build();
    }

    @Bean
    public NewTopic coderTopic(){
        return TopicBuilder.name("agent-coder-topic").build();
    }

    @Bean
    public NewTopic debuggerTopic(){
        return TopicBuilder.name("agent-debugger-topic").build();
    }
    @Bean public NewTopic dlqTopic() {
        return TopicBuilder.name("agent-dlq-topic").build();
    }

    @Bean public NewTopic resultTopic() {
        return TopicBuilder.name("agent-result-topic").build();
    }
}
