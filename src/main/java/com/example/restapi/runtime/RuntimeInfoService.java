package com.example.restapi.runtime;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Exposes runtime probe data and mutable feature flags for the Java 25 demo API.
 */
@Service
public class RuntimeInfoService {
    private final RuntimeProbe runtimeProbe;
    private final AtomicReference<FeatureFlags> flags;

    public RuntimeInfoService(RuntimeProbe runtimeProbe) {
        this.runtimeProbe = Objects.requireNonNull(runtimeProbe);
        this.flags = new AtomicReference<>(FeatureFlags.defaults());
    }

    public Map<String, Object> info() {
        Map<String, Object> body = new LinkedHashMap<>(runtimeProbe.snapshot());
        body.put("features", flags.get().asMap());
        body.put("summary", runtimeProbe.summarize());
        return body;
    }

    public Map<String, Boolean> features() {
        return flags.get().asMap();
    }

    public synchronized Map<String, Boolean> setFeature(String name, boolean enabled) {
        FeatureFlags current = flags.get();
        FeatureFlags next = enabled ? current.enable(name) : current.disable(name);
        flags.set(next);
        return next.asMap();
    }

    public boolean isFeatureEnabled(String name) {
        return flags.get().isEnabled(name);
    }

    public void requireFeature(String name) {
        if (!isFeatureEnabled(name)) {
            throw new IllegalStateException("Feature disabled: " + name);
        }
    }

    public String summary() {
        return runtimeProbe.summarize() + " features=" + flags.get().enabledNames();
    }
}
