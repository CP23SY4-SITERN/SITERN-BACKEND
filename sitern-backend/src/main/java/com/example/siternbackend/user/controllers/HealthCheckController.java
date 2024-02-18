package com.example.siternbackend.user.controllers;

import com.example.siternbackend.user.services.HealthCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class HealthCheckController {
    final HealthCheckService healthCheckService;

    @GetMapping("/details")
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok(healthCheckService.performHealthCheck());
    }
}
