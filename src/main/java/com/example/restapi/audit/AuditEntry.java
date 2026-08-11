package com.example.restapi.audit;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class AuditEntry {
    private final String id;
    private final AuditAction action;
    private final String actor;
    private final String resourceType;
    private final String resourceId;
    private final String details;
    private final Instant occurredAt;

    private AuditEntry(String id, AuditAction action, String actor, String resourceType,
                       String resourceId, String details, Instant occurredAt) {
        this.id = id;
        this.action = action;
        this.actor = actor;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.details = details;
        this.occurredAt = occurredAt;
    }

    public static AuditEntry of(AuditAction action, String actor, String resourceType, String resourceId, String details) {
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(actor, "actor");
        Objects.requireNonNull(resourceType, "resourceType");
        return new AuditEntry(UUID.randomUUID().toString(), action, actor.trim(), resourceType.trim(),
                resourceId == null ? "" : resourceId, details == null ? "" : details, Instant.now());
    }

    public String getId() { return id; }
    public AuditAction getAction() { return action; }
    public String getActor() { return actor; }
    public String getResourceType() { return resourceType; }
    public String getResourceId() { return resourceId; }
    public String getDetails() { return details; }
    public Instant getOccurredAt() { return occurredAt; }

    public boolean matchesResource(String type, String idValue) {
        return resourceType.equalsIgnoreCase(type) && resourceId.equals(idValue == null ? "" : idValue);
    }

    @Override
    public String toString() {
        return "AuditEntry{id='%s', action=%s, actor='%s', resource=%s/%s}".formatted(
                id, action, actor, resourceType, resourceId);
    }
}
