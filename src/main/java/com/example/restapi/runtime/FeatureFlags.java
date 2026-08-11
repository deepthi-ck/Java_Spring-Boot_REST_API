package com.example.restapi.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Feature flag registry used by demo endpoints on Java 25.
 */
public final class FeatureFlags {
    private final Map<String, Boolean> flags;

    public FeatureFlags(Map<String, Boolean> flags) {
        Objects.requireNonNull(flags, "flags");
        Map<String, Boolean> copy = new LinkedHashMap<>();
        for (Map.Entry<String, Boolean> entry : flags.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isBlank()) {
                continue;
            }
            copy.put(normalize(entry.getKey()), Boolean.TRUE.equals(entry.getValue()));
        }
        this.flags = Collections.unmodifiableMap(copy);
    }

    public static FeatureFlags defaults() {
        Map<String, Boolean> defaults = new LinkedHashMap<>();
        defaults.put("events", true);
        defaults.put("shipments", true);
        defaults.put("runtime-probe", true);
        defaults.put("strict-email", true);
        defaults.put("experimental-pricing", false);
        return new FeatureFlags(defaults);
    }

    public boolean isEnabled(String name) {
        Boolean value = flags.get(normalize(name));
        return Boolean.TRUE.equals(value);
    }

    public boolean isDisabled(String name) {
        return !isEnabled(name);
    }

    public FeatureFlags enable(String name) {
        Map<String, Boolean> next = new LinkedHashMap<>(flags);
        next.put(normalize(name), true);
        return new FeatureFlags(next);
    }

    public FeatureFlags disable(String name) {
        Map<String, Boolean> next = new LinkedHashMap<>(flags);
        next.put(normalize(name), false);
        return new FeatureFlags(next);
    }

    public List<String> enabledNames() {
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : flags.entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) {
                names.add(entry.getKey());
            }
        }
        return List.copyOf(names);
    }

    public Map<String, Boolean> asMap() {
        return flags;
    }

    public int size() {
        return flags.size();
    }

    private static String normalize(String name) {
        return name.trim().toLowerCase(Locale.ROOT);
    }
}
