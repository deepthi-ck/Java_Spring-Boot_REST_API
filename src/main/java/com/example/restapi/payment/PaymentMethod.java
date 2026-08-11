package com.example.restapi.payment;

import java.util.Locale;

public enum PaymentMethod {
    CARD, UPI, NET_BANKING, WALLET, COD;

    public static PaymentMethod from(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("method required");
        }
        return PaymentMethod.valueOf(raw.trim().toUpperCase(Locale.ROOT).replace('-', '_').replace(' ', '_'));
    }

    public boolean requiresOnlineAuth() {
        return this == CARD || this == UPI || this == NET_BANKING || this == WALLET;
    }

    public double processingFeePercent() {
        return switch (this) {
            case CARD -> 2.0;
            case UPI -> 0.0;
            case NET_BANKING -> 1.0;
            case WALLET -> 0.5;
            case COD -> 0.0;
        };
    }

    public String displayName() {
        return name().replace('_', ' ').toLowerCase(Locale.ROOT);
    }
}
