package com.example.restapi.catalog;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class Brand {
    private final String id;
    private final String name;
    private final String country;
    private boolean active;

    public Brand(String name, String country) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name).trim();
        this.country = country == null ? "US" : country.trim().toUpperCase(Locale.ROOT);
        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("brand name required");
        }
        this.active = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public boolean isActive() { return active; }

    public void deactivate() { this.active = false; }

    public void activate() { this.active = true; }

    public String slug() {
        return name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    @Override
    public String toString() {
        return "Brand{id='%s', name='%s', country='%s', active=%s}".formatted(id, name, country, active);
    }
}
