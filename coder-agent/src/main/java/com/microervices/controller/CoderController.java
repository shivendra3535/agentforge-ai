package com.microervices.controller;

import com.microervices.dto.CoderRequest;
import com.microervices.service.CoderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/coder")
public class CoderController {

    private final CoderService coderService;

    public CoderController(CoderService coderService) {
        this.coderService = coderService;
    }

    @PostMapping("/run")
    public String run(@RequestBody CoderRequest request) {
        return coderService.generateCode(
                request.getUserPrompt(),
                request.getPlanJson()
        );
    }
}
