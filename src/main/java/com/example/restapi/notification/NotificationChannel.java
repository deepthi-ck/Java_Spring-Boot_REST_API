package com.example.restapi.notification;

import java.util.Locale;

public enum NotificationChannel {
    EMAIL, SMS, PUSH, WEBHOOK, IN_APP;

    public static NotificationChannel from(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("channel required");
        }
        return NotificationChannel.valueOf(raw.trim().toUpperCase(Locale.ROOT).replace('-', '_'));
    }

    public boolean isExternal() {
        return this == EMAIL || this == SMS || this == WEBHOOK;
    }

    public int defaultPriority() {
        return switch (this) {
            case SMS, PUSH -> 1;
            case EMAIL, WEBHOOK -> 2;
            case IN_APP -> 3;
        };
    }

    public String displayName() {
        return name().replace('_', ' ').toLowerCase(Locale.ROOT);
    }
}
