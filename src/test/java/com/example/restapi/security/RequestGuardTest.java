package com.example.restapi.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestGuardTest {

    @Test
    void rejectsInvalidKey() {
        RequestGuard guard = new RequestGuard(new ApiKeyValidator("ok"), 5);
        assertFalse(guard.allow("client-a", "bad"));
    }

    @Test
    void rateLimitsAfterThreshold() {
        RequestGuard guard = new RequestGuard(new ApiKeyValidator("ok"), 2);
        assertTrue(guard.allow("client-a", "ok"));
        assertTrue(guard.allow("client-a", "ok"));
        assertFalse(guard.allow("client-a", "ok"));
        guard.reset("client-a");
        assertTrue(guard.allow("client-a", "ok"));
    }
}
