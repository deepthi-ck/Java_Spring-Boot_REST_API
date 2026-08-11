package com.example.restapi.controller;

import com.example.restapi.event.DomainEvent;
import com.example.restapi.event.EventPublisher;
import com.example.restapi.security.RequestGuard;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventPublisher eventPublisher;
    private final RequestGuard requestGuard;

    public EventController(EventPublisher eventPublisher, RequestGuard requestGuard) {
        this.eventPublisher = eventPublisher;
        this.requestGuard = requestGuard;
    }

    @PostMapping
    public Map<String, Object> publish(
            @RequestParam String type,
            @RequestParam String payload,
            @RequestHeader(value = "X-Api-Key", required = false) String apiKey,
            @RequestHeader(value = "X-Client-Id", required = false) String clientId) {
        enforce(clientId, apiKey);
        eventPublisher.publish(DomainEvent.of(type, payload));
        Map<String, Object> body = new HashMap<>();
        body.put("queued", true);
        body.put("size", eventPublisher.size());
        body.put("full", eventPublisher.isFull());
        return body;
    }

    @GetMapping
    public List<DomainEvent> drain(
            @RequestHeader(value = "X-Api-Key", required = false) String apiKey,
            @RequestHeader(value = "X-Client-Id", required = false) String clientId) {
        enforce(clientId, apiKey);
        return eventPublisher.drain();
    }

    @GetMapping("/peek")
    public List<DomainEvent> peek(
            @RequestParam(defaultValue = "10") int limit,
            @RequestHeader(value = "X-Api-Key", required = false) String apiKey,
            @RequestHeader(value = "X-Client-Id", required = false) String clientId) {
        enforce(clientId, apiKey);
        return eventPublisher.peek(limit);
    }

    @GetMapping("/by-type")
    public List<DomainEvent> byType(
            @RequestParam String type,
            @RequestHeader(value = "X-Api-Key", required = false) String apiKey,
            @RequestHeader(value = "X-Client-Id", required = false) String clientId) {
        enforce(clientId, apiKey);
        return eventPublisher.findByType(type);
    }

    @DeleteMapping
    public Map<String, Object> clearType(
            @RequestParam String type,
            @RequestHeader(value = "X-Api-Key", required = false) String apiKey,
            @RequestHeader(value = "X-Client-Id", required = false) String clientId) {
        enforce(clientId, apiKey);
        int removed = eventPublisher.clearByType(type);
        return Map.of("removed", removed, "remaining", eventPublisher.size());
    }

    private void enforce(String clientId, String apiKey) {
        if (!requestGuard.allow(clientId, apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid api key or rate limited");
        }
    }
}
