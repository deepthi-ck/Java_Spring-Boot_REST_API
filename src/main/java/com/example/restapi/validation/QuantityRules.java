package com.example.restapi.validation;

/**
 * Quantity and stock validation rules used by order and inventory flows.
 */
public final class QuantityRules {
    public static final int MIN_ORDER_QTY = 1;
    public static final int MAX_ORDER_QTY = 10_000;
    public static final int MAX_LINE_ITEMS = 50;

    private QuantityRules() {
    }

    public static boolean isValidOrderQuantity(int quantity) {
        return quantity >= MIN_ORDER_QTY && quantity <= MAX_ORDER_QTY;
    }

    public static void requireOrderQuantity(int quantity) {
        if (!isValidOrderQuantity(quantity)) {
            throw new IllegalArgumentException(
                    "quantity must be between " + MIN_ORDER_QTY + " and " + MAX_ORDER_QTY + ", got " + quantity);
        }
    }

    public static boolean canFulfill(int available, int requested) {
        requireOrderQuantity(requested);
        return available >= requested;
    }

    public static int clamp(int quantity) {
        if (quantity < MIN_ORDER_QTY) {
            return MIN_ORDER_QTY;
        }
        if (quantity > MAX_ORDER_QTY) {
            return MAX_ORDER_QTY;
        }
        return quantity;
    }

    public static int remainingAfter(int available, int requested) {
        requireOrderQuantity(requested);
        if (available < requested) {
            throw new IllegalArgumentException("insufficient stock: available=" + available + ", requested=" + requested);
        }
        return available - requested;
    }

    public static boolean isValidLineCount(int lineCount) {
        return lineCount >= 1 && lineCount <= MAX_LINE_ITEMS;
    }

    public static void requireLineCount(int lineCount) {
        if (!isValidLineCount(lineCount)) {
            throw new IllegalArgumentException(
                    "line count must be between 1 and " + MAX_LINE_ITEMS + ", got " + lineCount);
        }
    }

    public static int totalQuantity(int... quantities) {
        if (quantities == null || quantities.length == 0) {
            return 0;
        }
        requireLineCount(quantities.length);
        int total = 0;
        for (int quantity : quantities) {
            requireOrderQuantity(quantity);
            total += quantity;
        }
        return total;
    }

    public static boolean isPositiveStock(int stock) {
        return stock >= 0;
    }
}
