package com.example.restapi.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Payment {
    private final String id;
    private final String orderId;
    private final PaymentMethod method;
    private final BigDecimal amount;
    private PaymentStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Payment(String orderId, PaymentMethod method, BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.orderId = Objects.requireNonNull(orderId);
        this.method = Objects.requireNonNull(method);
        this.amount = Objects.requireNonNull(amount).setScale(2, RoundingMode.HALF_UP);
        if (this.amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.status = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
    }

    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public PaymentMethod getMethod() { return method; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public BigDecimal fee() {
        BigDecimal pct = BigDecimal.valueOf(method.processingFeePercent());
        return amount.multiply(pct).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal totalWithFee() {
        return amount.add(fee());
    }

    public void transitionTo(PaymentStatus next) {
        if (!status.canTransitionTo(next)) {
            throw new IllegalStateException("cannot move from " + status + " to " + next);
        }
        this.status = next;
        this.updatedAt = Instant.now();
    }

    public boolean isOpen() {
        return !status.isTerminal();
    }
}
