package com.example.restapi.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoneyUtilsTest {
    @Test
    void roundHalfUp() {
        assertEquals(10.13, MoneyUtils.round(10.125), 0.0001);
    }

    @Test
    void applyDiscount() {
        assertEquals(90.0, MoneyUtils.applyDiscount(100.0, 10.0), 0.0001);
    }

    @Test
    void applyDiscountInvalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> MoneyUtils.applyDiscount(100.0, -1));
    }

    @Test
    void tax() {
        assertEquals(8.0, MoneyUtils.tax(100.0, 8.0), 0.0001);
    }
}
