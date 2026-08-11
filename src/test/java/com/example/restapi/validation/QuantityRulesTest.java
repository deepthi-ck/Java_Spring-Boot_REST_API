package com.example.restapi.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuantityRulesTest {

    @Test
    void validatesQuantitiesAndStock() {
        assertTrue(QuantityRules.isValidOrderQuantity(1));
        assertFalse(QuantityRules.isValidOrderQuantity(0));
        assertEquals(5, QuantityRules.clamp(5));
        assertEquals(1, QuantityRules.clamp(0));
        assertTrue(QuantityRules.canFulfill(10, 3));
        assertEquals(7, QuantityRules.remainingAfter(10, 3));
        assertEquals(6, QuantityRules.totalQuantity(1, 2, 3));
        assertTrue(QuantityRules.isValidLineCount(3));
        assertTrue(QuantityRules.isPositiveStock(0));
    }

    @Test
    void rejectsInvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> QuantityRules.requireOrderQuantity(0));
        assertThrows(IllegalArgumentException.class, () -> QuantityRules.remainingAfter(1, 5));
        assertThrows(IllegalArgumentException.class, () -> QuantityRules.requireLineCount(0));
    }
}
