package com.example.restapi.policy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Objects;

public final class ShippingPolicy {
    private final String region;
    private final BigDecimal flatRate;
    private final BigDecimal freeAbove;
    private final int maxDays;

    public ShippingPolicy(String region, BigDecimal flatRate, BigDecimal freeAbove, int maxDays) {
        this.region = Objects.requireNonNull(region).trim().toUpperCase(Locale.ROOT);
        this.flatRate = Objects.requireNonNull(flatRate).setScale(2, RoundingMode.HALF_UP);
        this.freeAbove = Objects.requireNonNull(freeAbove).setScale(2, RoundingMode.HALF_UP);
        if (maxDays < 1) {
            throw new IllegalArgumentException("maxDays must be >= 1");
        }
        this.maxDays = maxDays;
    }

    public String getRegion() { return region; }
    public BigDecimal getFlatRate() { return flatRate; }
    public BigDecimal getFreeAbove() { return freeAbove; }
    public int getMaxDays() { return maxDays; }

    public BigDecimal quote(BigDecimal orderAmount) {
        if (orderAmount == null) {
            throw new IllegalArgumentException("orderAmount required");
        }
        if (orderAmount.compareTo(freeAbove) >= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return flatRate;
    }

    public boolean isExpressEligible(int requestedDays) {
        return requestedDays > 0 && requestedDays <= maxDays;
    }

    public String describe() {
        return "ShippingPolicy{region=%s, flat=%s, freeAbove=%s, maxDays=%d}".formatted(
                region, flatRate, freeAbove, maxDays);
    }
}
