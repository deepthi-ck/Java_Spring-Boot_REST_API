package com.example.restapi.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Thread-safe in-memory event queue used by the REST demo (Java 21).
 */
@Component
public class EventPublisher {
    private final List<DomainEvent> events = new CopyOnWriteArrayList<>();
    private final int maxQueueSize;

    public EventPublisher(@Value("${app.events.max-queue-size:1000}") int maxQueueSize) {
        if (maxQueueSize < 1) {
            throw new IllegalArgumentException("maxQueueSize must be >= 1");
        }
        this.maxQueueSize = maxQueueSize;
    }

    public synchronized void publish(DomainEvent event) {
        Objects.requireNonNull(event, "event");
        if (events.size() >= maxQueueSize) {
            events.remove(0);
        }
        events.add(event);
    }

    public synchronized List<DomainEvent> drain() {
        List<DomainEvent> copy = new ArrayList<>(events);
        events.clear();
        return Collections.unmodifiableList(copy);
    }

    public synchronized List<DomainEvent> peek(int limit) {
        int safeLimit = Math.max(0, Math.min(limit, events.size()));
        return List.copyOf(events.subList(0, safeLimit));
    }

    public List<DomainEvent> findByType(String type) {
        if (type == null || type.isBlank()) {
            return List.of();
        }
        String needle = type.trim().toLowerCase(Locale.ROOT);
        return events.stream()
                .filter(e -> e.getType().toLowerCase(Locale.ROOT).equals(needle))
                .collect(Collectors.toUnmodifiableList());
    }

    public synchronized int clearByType(String type) {
        List<DomainEvent> keep = events.stream()
                .filter(e -> !e.isType(type))
                .toList();
        int removed = events.size() - keep.size();
        events.clear();
        events.addAll(keep);
        return removed;
    }

    public int size() {
        return events.size();
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public boolean isFull() {
        return events.size() >= maxQueueSize;
    }
}
