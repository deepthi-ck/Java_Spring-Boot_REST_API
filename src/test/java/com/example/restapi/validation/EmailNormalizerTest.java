package com.example.restapi.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailNormalizerTest {

    @Test
    void normalizesAndValidates() {
        assertEquals("ada@example.com", EmailNormalizer.normalize("  Ada@Example.COM "));
        assertTrue(EmailNormalizer.looksValid("ada@example.com"));
        assertFalse(EmailNormalizer.looksValid("not-an-email"));
        assertEquals("example.com", EmailNormalizer.domainOf("ada@example.com"));
        assertEquals("ada", EmailNormalizer.localPartOf("ada@example.com"));
        assertTrue(EmailNormalizer.sameMailbox("Ada@Example.com", "ada@example.com"));
        assertTrue(EmailNormalizer.mask("ada@example.com").startsWith("a***@"));
    }

    @Test
    void requireValidRejectsBadInput() {
        assertThrows(IllegalArgumentException.class, () -> EmailNormalizer.requireValid("bad"));
    }
}
