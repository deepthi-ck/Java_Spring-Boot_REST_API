package com.example.restapi.policy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolicyEngineTest {
    @Test
    void appliesBestDiscountAndShipping() {
        PolicyEngine engine = new PolicyEngine();
        engine.addDiscount(new DiscountPolicy("small", new BigDecimal("5"), new BigDecimal("50"), true));
        engine.addDiscount(new DiscountPolicy("big", new BigDecimal("10"), new BigDecimal("100"), true));
        engine.addShipping(new ShippingPolicy("IN", new BigDecimal("40"), new BigDecimal("500"), 5));
        BigDecimal discounted = engine.applyBestDiscount(new BigDecimal("200"));
        assertEquals(new BigDecimal("180.00"), discounted);
        assertEquals(new BigDecimal("40.00"), engine.shippingQuote("IN", discounted));
        assertTrue(engine.policyCount() >= 3);
    }
}
