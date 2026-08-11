package com.example.restapi.runtime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RuntimeInfoServiceTest {

    @Test
    void exposesInfoAndFeatures() {
        RuntimeProbe probe = new RuntimeProbe("25");
        RuntimeInfoService service = new RuntimeInfoService(probe);

        assertTrue(service.info().containsKey("configuredJavaVersion"));
        assertEquals("25", service.info().get("configuredJavaVersion"));
        assertTrue(service.isFeatureEnabled("events"));

        service.setFeature("events", false);
        assertFalse(service.isFeatureEnabled("events"));
        assertThrows(IllegalStateException.class, () -> service.requireFeature("events"));
        assertTrue(service.summary().contains("java="));
    }
}
