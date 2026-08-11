package com.example.restapi.catalog;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CatalogService {
    private final ConcurrentHashMap<String, Brand> brands = new ConcurrentHashMap<>();

    public Brand create(String name, String country) {
        Brand brand = new Brand(name, country);
        brands.put(brand.getId(), brand);
        return brand;
    }

    public Optional<Brand> find(String id) {
        return Optional.ofNullable(brands.get(id));
    }

    public List<Brand> listActive() {
        return brands.values().stream().filter(Brand::isActive)
                .sorted(Comparator.comparing(Brand::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Brand> search(String query) {
        String needle = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        if (needle.isEmpty()) {
            return listActive();
        }
        return brands.values().stream()
                .filter(b -> b.getName().toLowerCase(Locale.ROOT).contains(needle) || b.slug().contains(needle))
                .sorted(Comparator.comparing(Brand::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public Brand deactivate(String id) {
        Brand brand = require(id);
        brand.deactivate();
        return brand;
    }

    public int count() { return brands.size(); }

    public List<Brand> all() { return List.copyOf(new ArrayList<>(brands.values())); }

    private Brand require(String id) {
        Brand brand = brands.get(id);
        if (brand == null) {
            throw new IllegalArgumentException("brand not found: " + id);
        }
        return brand;
    }
}
