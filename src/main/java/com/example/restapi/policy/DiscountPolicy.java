package com.example.restapi.policy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class DiscountPolicy {
    private final String name;
    private final BigDecimal percent;
    private final BigDecimal minOrderAmount;
    private final boolean active;

    public DiscountPolicy(String name, BigDecimal percent, BigDecimal minOrderAmount, boolean active) {
        this.name = Objects.requireNonNull(name).trim();
        this.percent = Objects.requireNonNull(percent);
        this.minOrderAmount = Objects.requireNonNull(minOrderAmount);
        if (this.percent.signum() < 0 || this.percent.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("percent must be 0..100");
        }
        this.active = active;
    }

    public String getName() { return name; }
    public BigDecimal getPercent() { return percent; }
    public BigDecimal getMinOrderAmount() { return minOrderAmount; }
    public boolean isActive() { return active; }

    public boolean appliesTo(BigDecimal orderAmount) {
        return active && orderAmount != null && orderAmount.compareTo(minOrderAmount) >= 0;
    }

    public BigDecimal discountFor(BigDecimal orderAmount) {
        if (!appliesTo(orderAmount)) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return orderAmount.multiply(percent)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal netAmount(BigDecimal orderAmount) {
        return orderAmount.subtract(discountFor(orderAmount)).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }
}
