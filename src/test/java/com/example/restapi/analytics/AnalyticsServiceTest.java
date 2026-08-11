package com.example.restapi.analytics;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticsServiceTest {
    @Test
    void recordsAndSummarizes() {
        AnalyticsService service = new AnalyticsService();
        service.record("latency", 10, "api=items");
        service.record("latency", 20, "api=items");
        Map<String, Object> summary = service.summarize("latency");
        assertEquals(2, summary.get("count"));
        assertEquals(15.0, (Double) summary.get("avg"), 0.001);
        assertEquals(2, service.latest(5).size());
    }
}
