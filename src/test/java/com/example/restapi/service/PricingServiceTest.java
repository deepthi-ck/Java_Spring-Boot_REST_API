package com.example.restapi.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {
    @Test
    void finalizeTotalAppliesTax() {
        assertEquals(108.0, new PricingService(8.0, 0.0).finalizeTotal(100.0), 0.0001);
    }

    @Test
    void finalizeTotalAppliesDiscountThenTax() {
        assertEquals(99.0, new PricingService(10.0, 10.0).finalizeTotal(100.0), 0.0001);
    }
}
