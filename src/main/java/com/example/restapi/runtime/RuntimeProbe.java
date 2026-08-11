package com.example.restapi.runtime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Runtime metadata helpers for the Java 25 REST API sample.
 */
@Component
public class RuntimeProbe {
    private final String configuredJavaVersion;
    private final Instant startedAt;

    public RuntimeProbe(@Value("${app.runtime.java-version:25}") String configuredJavaVersion) {
        this.configuredJavaVersion = Objects.requireNonNull(configuredJavaVersion).trim();
        this.startedAt = Instant.now();
    }

    public String configuredJavaVersion() {
        return configuredJavaVersion;
    }

    public String actualJavaVersion() {
        return System.getProperty("java.version", "unknown");
    }

    public String javaVendor() {
        return System.getProperty("java.vendor", "unknown");
    }

    public String jvmName() {
        return System.getProperty("java.vm.name", "unknown");
    }

    public boolean matchesConfiguredMajor() {
        String actual = actualJavaVersion();
        return actual.startsWith(configuredJavaVersion)
                || actual.startsWith(configuredJavaVersion + ".")
                || actual.contains("\"%s\"".formatted(configuredJavaVersion));
    }

    public Duration uptime() {
        return Duration.between(startedAt, Instant.now());
    }

    public long uptimeSeconds() {
        return Math.max(0L, uptime().toSeconds());
    }

    public List<String> jvmArguments() {
        RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
        return List.copyOf(mx.getInputArguments());
    }

    public Map<String, Object> snapshot() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("configuredJavaVersion", configuredJavaVersion());
        body.put("actualJavaVersion", actualJavaVersion());
        body.put("javaVendor", javaVendor());
        body.put("jvmName", jvmName());
        body.put("matchesConfiguredMajor", matchesConfiguredMajor());
        body.put("uptimeSeconds", uptimeSeconds());
        body.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        body.put("maxMemoryMb", Runtime.getRuntime().maxMemory() / (1024 * 1024));
        body.put("freeMemoryMb", Runtime.getRuntime().freeMemory() / (1024 * 1024));
        body.put("locale", Locale.getDefault().toLanguageTag());
        body.put("startedAt", startedAt.toString());
        return body;
    }

    public String summarize() {
        return "java=%s configured=%s uptime=%ss".formatted(
                actualJavaVersion(), configuredJavaVersion(), uptimeSeconds());
    }
}
