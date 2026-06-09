package com.microervices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlannerAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlannerAgentApplication.class,args);
    }
}
