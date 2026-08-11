package com.example.restapi.runtime;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FeatureFlagsTest {

    @Test
    void defaultsAndToggle() {
        FeatureFlags flags = FeatureFlags.defaults();
        assertTrue(flags.isEnabled("events"));
        assertFalse(flags.isEnabled("experimental-pricing"));
        assertTrue(flags.enabledNames().contains("events"));

        FeatureFlags next = flags.enable("experimental-pricing").disable("shipments");
        assertTrue(next.isEnabled("experimental-pricing"));
        assertTrue(next.isDisabled("shipments"));
        assertEquals(flags.size(), next.size());
    }

    @Test
    void ignoresBlankKeys() {
        FeatureFlags flags = new FeatureFlags(Map.of(" ", true, "ok", true));
        assertTrue(flags.isEnabled("OK"));
        assertEquals(1, flags.size());
    }
}
