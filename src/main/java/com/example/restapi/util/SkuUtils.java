package com.example.restapi.util;

public final class SkuUtils {
    private SkuUtils() {}

    public static String normalize(String sku) {
        return sku == null ? null : sku.trim().toUpperCase();
    }

    public static boolean isValid(String sku) {
        if (sku == null || sku.isBlank()) {
            return false;
        }
        return normalize(sku).matches("[A-Z0-9\\-]{3,32}");
    }
}
