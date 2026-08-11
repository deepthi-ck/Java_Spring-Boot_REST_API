package com.example.restapi.report;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {
    @Test
    void generatesSalesReport() {
        ReportService service = new ReportService(new ReportBuilder());
        Map<String, Object> report = service.generateSales(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 10), "csv", 12, 2400);
        assertEquals("sales", report.get("type"));
        assertTrue(report.containsKey("csv"));
        assertEquals(1L, service.generatedCount());
    }
}
