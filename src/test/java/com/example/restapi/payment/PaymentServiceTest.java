package com.example.restapi.payment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentServiceTest {
    @Test
    void authorizeAndCapture() {
        PaymentService service = new PaymentService();
        Payment payment = service.create("ord-1", PaymentMethod.UPI, new BigDecimal("100.00"));
        service.authorize(payment.getId());
        Payment captured = service.capture(payment.getId());
        assertEquals(PaymentStatus.CAPTURED, captured.getStatus());
        assertTrue(captured.getAmount().compareTo(BigDecimal.ZERO) > 0);
        assertEquals(0, service.openPayments().size());
    }
}
