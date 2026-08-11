package com.example.restapi.payment;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

public enum PaymentStatus {
    PENDING, AUTHORIZED, CAPTURED, FAILED, REFUNDED, CANCELLED;

    private static final Set<PaymentStatus> TERMINAL = EnumSet.of(CAPTURED, FAILED, REFUNDED, CANCELLED);

    public static PaymentStatus from(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("status required");
        }
        return PaymentStatus.valueOf(raw.trim().toUpperCase(Locale.ROOT));
    }

    public boolean isTerminal() {
        return TERMINAL.contains(this);
    }

    public boolean canTransitionTo(PaymentStatus next) {
        if (next == null || this == next) {
            return false;
        }
        return switch (this) {
            case PENDING -> next == AUTHORIZED || next == FAILED || next == CANCELLED;
            case AUTHORIZED -> next == CAPTURED || next == FAILED || next == CANCELLED;
            case CAPTURED -> next == REFUNDED;
            case FAILED, REFUNDED, CANCELLED -> false;
        };
    }

    public String label() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT);
    }
}
