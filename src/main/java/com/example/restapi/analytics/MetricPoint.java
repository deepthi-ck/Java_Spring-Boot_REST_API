package com.example.restapi.analytics;

import java.time.Instant;
import java.util.Objects;

public final class MetricPoint {
    private final String name;
    private final double value;
    private final String tags;
    private final Instant recordedAt;

    public MetricPoint(String name, double value, String tags) {
        this.name = Objects.requireNonNull(name).trim();
        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("metric name required");
        }
        this.value = value;
        this.tags = tags == null ? "" : tags.trim();
        this.recordedAt = Instant.now();
    }

    public String getName() { return name; }
    public double getValue() { return value; }
    public String getTags() { return tags; }
    public Instant getRecordedAt() { return recordedAt; }

    public boolean matches(String metricName) {
        return name.equalsIgnoreCase(metricName);
    }

    public MetricPoint scale(double factor) {
        return new MetricPoint(name, value * factor, tags);
    }

    @Override
    public String toString() {
        return "MetricPoint{name='%s', value=%s, tags='%s'}".formatted(name, value, tags);
    }
}
