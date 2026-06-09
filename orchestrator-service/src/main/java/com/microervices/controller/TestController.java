package com.microervices.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orchestrator")
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Orchestrator";
    }
}
