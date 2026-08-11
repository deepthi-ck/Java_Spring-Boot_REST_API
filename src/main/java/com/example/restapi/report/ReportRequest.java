package com.example.restapi.report;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public final class ReportRequest {
    private final String type;
    private final LocalDate from;
    private final LocalDate to;
    private final String format;

    public ReportRequest(String type, LocalDate from, LocalDate to, String format) {
        this.type = Objects.requireNonNull(type).trim().toLowerCase(Locale.ROOT);
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("to must be on/after from");
        }
        this.format = format == null || format.isBlank() ? "json" : format.trim().toLowerCase(Locale.ROOT);
    }

    public String getType() { return type; }
    public LocalDate getFrom() { return from; }
    public LocalDate getTo() { return to; }
    public String getFormat() { return format; }

    public long daySpan() {
        return java.time.temporal.ChronoUnit.DAYS.between(from, to) + 1;
    }

    public boolean isCsv() { return "csv".equals(format); }

    public boolean isSales() { return "sales".equals(type); }

    public boolean isInventory() { return "inventory".equals(type); }

    @Override
    public String toString() {
        return "ReportRequest{type='%s', from=%s, to=%s, format='%s'}".formatted(type, from, to, format);
    }
}
