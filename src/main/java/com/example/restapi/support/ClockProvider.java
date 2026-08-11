package com.example.restapi.support;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class ClockProvider {
    private final Clock clock;

    public ClockProvider() {
        this(Clock.systemUTC());
    }

    public ClockProvider(Clock clock) {
        this.clock = clock == null ? Clock.systemUTC() : clock;
    }

    public Instant now() {
        return Instant.now(clock);
    }

    public LocalDate today() {
        return LocalDate.now(clock);
    }

    public long epochMilli() {
        return now().toEpochMilli();
    }

    public ZoneOffset zoneOffset() {
        return ZoneOffset.UTC;
    }

    public boolean isBefore(Instant other) {
        return now().isBefore(other);
    }

    public boolean isAfter(Instant other) {
        return now().isAfter(other);
    }
}
