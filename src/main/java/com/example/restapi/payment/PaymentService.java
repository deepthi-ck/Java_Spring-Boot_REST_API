package com.example.restapi.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {
    private final ConcurrentHashMap<String, Payment> payments = new ConcurrentHashMap<>();

    public Payment create(String orderId, PaymentMethod method, BigDecimal amount) {
        Payment payment = new Payment(orderId, method, amount);
        payments.put(payment.getId(), payment);
        return payment;
    }

    public Optional<Payment> find(String id) {
        return Optional.ofNullable(payments.get(id));
    }

    public Payment authorize(String id) {
        Payment payment = require(id);
        payment.transitionTo(PaymentStatus.AUTHORIZED);
        return payment;
    }

    public Payment capture(String id) {
        Payment payment = require(id);
        if (payment.getStatus() == PaymentStatus.PENDING && payment.getMethod().requiresOnlineAuth()) {
            payment.transitionTo(PaymentStatus.AUTHORIZED);
        }
        payment.transitionTo(PaymentStatus.CAPTURED);
        return payment;
    }

    public Payment fail(String id) {
        Payment payment = require(id);
        payment.transitionTo(PaymentStatus.FAILED);
        return payment;
    }

    public Payment refund(String id) {
        Payment payment = require(id);
        payment.transitionTo(PaymentStatus.REFUNDED);
        return payment;
    }

    public List<Payment> byOrder(String orderId) {
        Objects.requireNonNull(orderId);
        List<Payment> result = new ArrayList<>();
        for (Payment payment : payments.values()) {
            if (payment.getOrderId().equals(orderId)) {
                result.add(payment);
            }
        }
        return List.copyOf(result);
    }

    public List<Payment> openPayments() {
        return payments.values().stream().filter(Payment::isOpen).toList();
    }

    public int count() { return payments.size(); }

    private Payment require(String id) {
        Payment payment = payments.get(id);
        if (payment == null) {
            throw new IllegalArgumentException("payment not found: " + id);
        }
        return payment;
    }
}
