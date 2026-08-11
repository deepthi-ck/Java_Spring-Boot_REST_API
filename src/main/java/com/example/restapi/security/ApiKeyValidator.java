package com.example.restapi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;

/**
 * Simple API-key checker for demo endpoints (Java 21).
 */
@Component
public class ApiKeyValidator {
    private final String expectedKey;
    private final byte[] expectedDigest;

    public ApiKeyValidator(@Value("${app.security.api-key:demo-key}") String expectedKey) {
        this.expectedKey = Objects.requireNonNull(expectedKey, "expectedKey").trim();
        this.expectedDigest = sha256(this.expectedKey);
    }

    public boolean isValid(String provided) {
        if (provided == null || provided.isBlank()) {
            return false;
        }
        String candidate = provided.trim();
        if (candidate.equals(expectedKey)) {
            return true;
        }
        return MessageDigest.isEqual(expectedDigest, sha256(candidate));
    }

    public boolean isValidHeader(String headerValue) {
        if (headerValue == null) {
            return false;
        }
        String value = headerValue.trim();
        if (value.toLowerCase(Locale.ROOT).startsWith("bearer ")) {
            value = value.substring(7).trim();
        }
        if (value.toLowerCase(Locale.ROOT).startsWith("apikey ")) {
            value = value.substring(7).trim();
        }
        return isValid(value);
    }

    public String mask() {
        if (expectedKey.length() <= 4) {
            return "****";
        }
        return expectedKey.substring(0, 2) + "****" + expectedKey.substring(expectedKey.length() - 2);
    }

    public int expectedLength() {
        return expectedKey.length();
    }

    private static byte[] sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 unavailable", ex);
        }
    }
}
