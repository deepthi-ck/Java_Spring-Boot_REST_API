package com.example.restapi.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiKeyValidatorTest {

    @Test
    void acceptsExactKey() {
        ApiKeyValidator validator = new ApiKeyValidator("demo-key-java25");
        assertTrue(validator.isValid("demo-key-java25"));
        assertFalse(validator.isValid("wrong"));
        assertFalse(validator.isValid(null));
        assertFalse(validator.isValid(" "));
    }

    @Test
    void acceptsBearerAndApiKeyHeaders() {
        ApiKeyValidator validator = new ApiKeyValidator("secret");
        assertTrue(validator.isValidHeader("Bearer secret"));
        assertTrue(validator.isValidHeader("ApiKey secret"));
        assertFalse(validator.isValidHeader("Bearer other"));
        assertEquals(6, validator.expectedLength());
        assertTrue(validator.mask().contains("****"));
    }
}