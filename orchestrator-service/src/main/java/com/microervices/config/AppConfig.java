package com.microervices.config;

import com.microervices.dto.AgentTask;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, AgentTask> agentTaskConsumerFactory() {
        JsonDeserializer<AgentTask> deserializer = new JsonDeserializer<>(AgentTask.class, false);
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "orchestrator-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new org.apache.kafka.common.serialization.StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgentTask>
    agentTaskKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, AgentTask>();
        factory.setConsumerFactory(agentTaskConsumerFactory());
        return factory;
    }
}
