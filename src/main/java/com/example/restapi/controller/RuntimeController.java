package com.example.restapi.controller;

import com.example.restapi.runtime.RuntimeInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/runtime")
public class RuntimeController {
    private final RuntimeInfoService runtimeInfoService;

    public RuntimeController(RuntimeInfoService runtimeInfoService) {
        this.runtimeInfoService = runtimeInfoService;
    }

    @GetMapping
    public Map<String, Object> info() {
        return runtimeInfoService.info();
    }

    @GetMapping("/features")
    public Map<String, Boolean> features() {
        return runtimeInfoService.features();
    }

    @PutMapping("/features/{name}")
    public Map<String, Boolean> setFeature(
            @PathVariable String name,
            @RequestParam boolean enabled) {
        return runtimeInfoService.setFeature(name, enabled);
    }

    @GetMapping("/summary")
    public Map<String, String> summary() {
        return Map.of("summary", runtimeInfoService.summary());
    }
}
