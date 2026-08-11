package com.example.restapi.controller;

import com.example.restapi.analytics.AnalyticsService;
import com.example.restapi.analytics.MetricPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PostMapping("/metrics")
    public MetricPoint record(
            @RequestParam String name,
            @RequestParam double value,
            @RequestParam(required = false) String tags) {
        return analyticsService.record(name, value, tags);
    }

    @GetMapping("/metrics")
    public List<MetricPoint> latest(@RequestParam(defaultValue = "25") int limit) {
        return analyticsService.latest(limit);
    }

    @GetMapping("/summary")
    public Map<String, Object> summary(@RequestParam String name) {
        return analyticsService.summarize(name);
    }

    @GetMapping("/overview")
    public Map<String, Object> overview() {
        Map<String, Object> body = new HashMap<>();
        body.put("points", analyticsService.size());
        body.put("latest", analyticsService.latest(5));
        return body;
    }
}
