package com.microervices.controller;

import com.microervices.dto.DebugRequest;
import com.microervices.service.DebuggerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/debugger")
public class DebuggerController {

    private final DebuggerService debuggerService;

    public DebuggerController(DebuggerService debuggerService) {
        this.debuggerService = debuggerService;
    }

    @PostMapping("/run")
    public String run(@RequestBody DebugRequest request) {
        return debuggerService.debug(request.getCodeJson());
    }
}
