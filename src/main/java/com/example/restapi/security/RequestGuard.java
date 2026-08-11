package com.example.restapi.security;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lightweight request guard combining API-key checks and simple rate limiting.
 */
@Component
public class RequestGuard {
    private final ApiKeyValidator apiKeyValidator;
    private final Map<String, Window> windows = new ConcurrentHashMap<>();
    private final int maxRequestsPerMinute;

    public RequestGuard(ApiKeyValidator apiKeyValidator) {
        this(apiKeyValidator, 120);
    }

    public RequestGuard(ApiKeyValidator apiKeyValidator, int maxRequestsPerMinute) {
        this.apiKeyValidator = Objects.requireNonNull(apiKeyValidator);
        if (maxRequestsPerMinute < 1) {
            throw new IllegalArgumentException("maxRequestsPerMinute must be >= 1");
        }
        this.maxRequestsPerMinute = maxRequestsPerMinute;
    }

    public boolean allow(String clientId, String apiKey) {
        if (!apiKeyValidator.isValid(apiKey)) {
            return false;
        }
        String key = clientId == null || clientId.isBlank() ? "anonymous" : clientId.trim();
        Window window = windows.computeIfAbsent(key, ignored -> new Window());
        return window.tryAcquire(maxRequestsPerMinute);
    }

    public boolean allowHeader(String clientId, String authorizationHeader) {
        if (!apiKeyValidator.isValidHeader(authorizationHeader)) {
            return false;
        }
        String key = clientId == null || clientId.isBlank() ? "anonymous" : clientId.trim();
        Window window = windows.computeIfAbsent(key, ignored -> new Window());
        return window.tryAcquire(maxRequestsPerMinute);
    }

    public int remaining(String clientId) {
        String key = clientId == null || clientId.isBlank() ? "anonymous" : clientId.trim();
        Window window = windows.get(key);
        if (window == null) {
            return maxRequestsPerMinute;
        }
        return Math.max(0, maxRequestsPerMinute - window.countInCurrentMinute());
    }

    public void reset(String clientId) {
        if (clientId != null) {
            windows.remove(clientId.trim());
        }
    }

    public int trackedClients() {
        return windows.size();
    }

    private static final class Window {
        private volatile long minuteEpoch;
        private final AtomicInteger count = new AtomicInteger();

        synchronized boolean tryAcquire(int limit) {
            long nowMinute = Instant.now().getEpochSecond() / 60;
            if (nowMinute != minuteEpoch) {
                minuteEpoch = nowMinute;
                count.set(0);
            }
            if (count.get() >= limit) {
                return false;
            }
            count.incrementAndGet();
            return true;
        }

        synchronized int countInCurrentMinute() {
            long nowMinute = Instant.now().getEpochSecond() / 60;
            if (nowMinute != minuteEpoch) {
                return 0;
            }
            return count.get();
        }
    }
}
