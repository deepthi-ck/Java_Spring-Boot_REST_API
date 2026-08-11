package com.example.restapi.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {
    @Test
    void finalizeTotalAppliesTax() {
        PricingService pricing = new PricingService(8.0, 0.0);
        assertEquals(108.0, pricing.finalizeTotal(100.0), 0.0001);
    }

    @Test
    void finalizeTotalAppliesDiscountThenTax() {
        PricingService pricing = new PricingService(10.0, 10.0);
        assertEquals(99.0, pricing.finalizeTotal(100.0), 0.0001);
    }
}
