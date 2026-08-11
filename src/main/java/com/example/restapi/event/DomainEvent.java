package com.example.restapi.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Immutable domain event recorded by the in-memory publisher (Java 21).
 */
public final class DomainEvent {
    private final String id;
    private final String type;
    private final String payload;
    private final Instant occurredAt;

    private DomainEvent(String id, String type, String payload, Instant occurredAt) {
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.occurredAt = occurredAt;
    }

    public static DomainEvent of(String type, String payload) {
        Objects.requireNonNull(type, "type");
        String safePayload = payload == null ? "" : payload.trim();
        return new DomainEvent(UUID.randomUUID().toString(), type.trim(), safePayload, Instant.now());
    }

    public static DomainEvent restore(String id, String type, String payload, Instant occurredAt) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(occurredAt, "occurredAt");
        return new DomainEvent(id, type, payload == null ? "" : payload, occurredAt);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public boolean isType(String expected) {
        return type.equalsIgnoreCase(expected);
    }

    public DomainEvent withPayload(String newPayload) {
        return new DomainEvent(id, type, newPayload == null ? "" : newPayload, occurredAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEvent that)) {
            return false;
        }
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "DomainEvent{id='%s', type='%s', occurredAt=%s}".formatted(id, type, occurredAt);
    }
}
