package com.example.restapi.support;

import java.util.Comparator;
import java.util.Locale;

public enum SortOrder {
    ASC, DESC;

    public static SortOrder from(String raw) {
        if (raw == null || raw.isBlank()) {
            return ASC;
        }
        return SortOrder.valueOf(raw.trim().toUpperCase(Locale.ROOT));
    }

    public boolean isAscending() {
        return this == ASC;
    }

    public <T> Comparator<T> apply(Comparator<T> base) {
        return isAscending() ? base : base.reversed();
    }

    public SortOrder opposite() {
        return this == ASC ? DESC : ASC;
    }

    public String label() {
        return name().toLowerCase(Locale.ROOT);
    }
}
