package com.example.restapi.controller;

import com.example.restapi.report.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/sales")
    public Map<String, Object> sales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "json") String format,
            @RequestParam(defaultValue = "10") long records,
            @RequestParam(defaultValue = "1000") double total) {
        return reportService.generateSales(from, to, format, records, total);
    }

    @GetMapping("/inventory")
    public Map<String, Object> inventory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "json") String format,
            @RequestParam(defaultValue = "25") long records,
            @RequestParam(defaultValue = "5000") double total) {
        return reportService.generateInventory(from, to, format, records, total);
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> body = new HashMap<>();
        body.put("generated", reportService.generatedCount());
        body.put("supportsSales", reportService.supports("sales"));
        body.put("supportsInventory", reportService.supports("inventory"));
        return body;
    }
}
