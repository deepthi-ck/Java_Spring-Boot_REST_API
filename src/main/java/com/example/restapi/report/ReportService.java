package com.example.restapi.report;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReportService {
    private final ReportBuilder reportBuilder;
    private final AtomicLong generated = new AtomicLong();

    public ReportService(ReportBuilder reportBuilder) {
        this.reportBuilder = Objects.requireNonNull(reportBuilder);
    }

    public Map<String, Object> generateSales(LocalDate from, LocalDate to, String format, long records, double total) {
        ReportRequest request = new ReportRequest("sales", from, to, format);
        generated.incrementAndGet();
        Map<String, Object> report = reportBuilder.build(request, records, total);
        if (request.isCsv()) {
            report.put("csv", reportBuilder.toCsv(report));
        }
        return report;
    }

    public Map<String, Object> generateInventory(LocalDate from, LocalDate to, String format, long records, double total) {
        ReportRequest request = new ReportRequest("inventory", from, to, format);
        generated.incrementAndGet();
        Map<String, Object> report = reportBuilder.build(request, records, total);
        if (request.isCsv()) {
            report.put("csv", reportBuilder.toCsv(report));
        }
        return report;
    }

    public boolean supports(String type) {
        return reportBuilder.supports(type);
    }

    public long generatedCount() {
        return generated.get();
    }
}
