package com.example.restapi.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkuUtilsTest {
    @Test
    void normalizeUppercases() {
        assertEquals("ABC-1", SkuUtils.normalize(" abc-1 "));
    }

    @Test
    void isValidAcceptsGoodSku() {
        assertTrue(SkuUtils.isValid("ITEM-001"));
    }

    @Test
    void isValidRejectsShort() {
        assertFalse(SkuUtils.isValid("AB"));
    }
}
