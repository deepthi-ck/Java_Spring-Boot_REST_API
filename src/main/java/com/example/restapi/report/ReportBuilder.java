package com.example.restapi.report;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ReportBuilder {
    public Map<String, Object> build(ReportRequest request, long records, double totalValue) {
        Objects.requireNonNull(request);
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("type", request.getType());
        report.put("from", request.getFrom().toString());
        report.put("to", request.getTo().toString());
        report.put("format", request.getFormat());
        report.put("daySpan", request.daySpan());
        report.put("records", records);
        report.put("totalValue", totalValue);
        report.put("averagePerDay", request.daySpan() == 0 ? 0.0 : totalValue / request.daySpan());
        report.put("title", titleFor(request));
        return report;
    }

    public String toCsv(Map<String, Object> report) {
        StringBuilder sb = new StringBuilder();
        sb.append("key,value\n");
        for (Map.Entry<String, Object> entry : report.entrySet()) {
            sb.append(entry.getKey()).append(',').append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }

    public String titleFor(ReportRequest request) {
        String kind = request.getType().substring(0, 1).toUpperCase() + request.getType().substring(1);
        return kind + " report (" + request.getFrom() + " to " + request.getTo() + ")";
    }

    public boolean supports(String type) {
        return "sales".equalsIgnoreCase(type) || "inventory".equalsIgnoreCase(type) || "orders".equalsIgnoreCase(type);
    }
}
