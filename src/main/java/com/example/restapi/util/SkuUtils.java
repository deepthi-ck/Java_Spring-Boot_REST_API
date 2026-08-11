package com.example.restapi.util;

public final class SkuUtils {
    private SkuUtils() {}

    public static String normalize(String sku) {
        if (sku == null) {
            return null;
        }
        return sku.trim().toUpperCase();
    }

    public static boolean isValid(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            return false;
        }
        String normalized = normalize(sku);
        return normalized.matches("[A-Z0-9\\-]{3,32}");
    }
}
