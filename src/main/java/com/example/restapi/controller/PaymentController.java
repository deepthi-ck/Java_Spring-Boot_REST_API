package com.example.restapi.controller;

import com.example.restapi.payment.Payment;
import com.example.restapi.payment.PaymentMethod;
import com.example.restapi.payment.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment create(
            @RequestParam String orderId,
            @RequestParam String method,
            @RequestParam BigDecimal amount) {
        return paymentService.create(orderId, PaymentMethod.from(method), amount);
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable String id) {
        return paymentService.find(id).orElseThrow(() -> new IllegalArgumentException("not found"));
    }

    @PostMapping("/{id}/authorize")
    public Payment authorize(@PathVariable String id) {
        return paymentService.authorize(id);
    }

    @PostMapping("/{id}/capture")
    public Payment capture(@PathVariable String id) {
        return paymentService.capture(id);
    }

    @PostMapping("/{id}/refund")
    public Payment refund(@PathVariable String id) {
        return paymentService.refund(id);
    }

    @GetMapping
    public List<Payment> open() {
        return paymentService.openPayments();
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> body = new HashMap<>();
        body.put("total", paymentService.count());
        body.put("open", paymentService.openPayments().size());
        return body;
    }
}
