package com.example.restapi.validation;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Email normalization helpers shared by customer flows (Java 21).
 */
public final class EmailNormalizer {
    private static final Pattern SIMPLE_EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private EmailNormalizer() {
    }

    public static String normalize(String email) {
        if (email == null) {
            return null;
        }
        String trimmed = email.trim().toLowerCase(Locale.ROOT);
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static boolean looksValid(String email) {
        String normalized = normalize(email);
        if (normalized == null) {
            return false;
        }
        if (normalized.length() > 254) {
            return false;
        }
        return SIMPLE_EMAIL.matcher(normalized).matches();
    }

    public static String requireValid(String email) {
        String normalized = normalize(email);
        if (!looksValid(normalized)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        return Objects.requireNonNull(normalized);
    }

    public static String domainOf(String email) {
        String normalized = requireValid(email);
        int at = normalized.lastIndexOf('@');
        return normalized.substring(at + 1);
    }

    public static String localPartOf(String email) {
        String normalized = requireValid(email);
        int at = normalized.indexOf('@');
        return normalized.substring(0, at);
    }

    public static boolean sameMailbox(String left, String right) {
        String a = normalize(left);
        String b = normalize(right);
        return a != null && a.equals(b);
    }

    public static String mask(String email) {
        if (!looksValid(email)) {
            return "***";
        }
        String local = localPartOf(email);
        String domain = domainOf(email);
        String visible = local.length() <= 1 ? "*" : local.substring(0, 1) + "***";
        return visible + "@" + domain;
    }
}
