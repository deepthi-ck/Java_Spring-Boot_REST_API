package com.example.restapi.controller;

import com.example.restapi.policy.DiscountPolicy;
import com.example.restapi.policy.PolicyEngine;
import com.example.restapi.policy.ShippingPolicy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
    private final PolicyEngine policyEngine;

    public PolicyController(PolicyEngine policyEngine) {
        this.policyEngine = policyEngine;
    }

    @PostMapping("/discounts")
    public DiscountPolicy addDiscount(
            @RequestParam String name,
            @RequestParam BigDecimal percent,
            @RequestParam BigDecimal minOrderAmount,
            @RequestParam(defaultValue = "true") boolean active) {
        return policyEngine.addDiscount(new DiscountPolicy(name, percent, minOrderAmount, active));
    }

    @PostMapping("/shipping")
    public ShippingPolicy addShipping(
            @RequestParam String region,
            @RequestParam BigDecimal flatRate,
            @RequestParam BigDecimal freeAbove,
            @RequestParam int maxDays) {
        return policyEngine.addShipping(new ShippingPolicy(region, flatRate, freeAbove, maxDays));
    }

    @GetMapping("/quote")
    public Map<String, Object> quote(@RequestParam String region, @RequestParam BigDecimal amount) {
        Map<String, Object> body = new HashMap<>();
        BigDecimal discounted = policyEngine.applyBestDiscount(amount);
        body.put("discountedAmount", discounted);
        body.put("shipping", policyEngine.shippingQuote(region, discounted));
        body.put("bestDiscount", policyEngine.bestDiscount(amount).map(DiscountPolicy::getName).orElse(null));
        return body;
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> body = new HashMap<>();
        body.put("discounts", policyEngine.discounts().size());
        body.put("shipping", policyEngine.shippingPolicies().size());
        body.put("total", policyEngine.policyCount());
        return body;
    }
}
