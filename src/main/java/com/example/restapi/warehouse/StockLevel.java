package com.example.restapi.warehouse;

import java.util.Objects;

public class StockLevel {
    private final String sku;
    private final String warehouseCode;
    private int onHand;
    private int reserved;

    public StockLevel(String sku, String warehouseCode, int onHand) {
        this.sku = Objects.requireNonNull(sku).trim().toUpperCase();
        this.warehouseCode = Objects.requireNonNull(warehouseCode).trim().toUpperCase();
        if (onHand < 0) {
            throw new IllegalArgumentException("onHand must be >= 0");
        }
        this.onHand = onHand;
        this.reserved = 0;
    }

    public String getSku() { return sku; }
    public String getWarehouseCode() { return warehouseCode; }
    public int getOnHand() { return onHand; }
    public int getReserved() { return reserved; }

    public int available() { return onHand - reserved; }

    public void receive(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("receive qty must be > 0");
        }
        onHand += qty;
    }

    public void reserve(int qty) {
        if (qty <= 0 || qty > available()) {
            throw new IllegalStateException("cannot reserve " + qty + " for " + sku);
        }
        reserved += qty;
    }

    public void ship(int qty) {
        if (qty <= 0 || qty > reserved) {
            throw new IllegalStateException("cannot ship " + qty + " for " + sku);
        }
        reserved -= qty;
        onHand -= qty;
    }

    public void releaseReserve(int qty) {
        if (qty <= 0 || qty > reserved) {
            throw new IllegalArgumentException("invalid release");
        }
        reserved -= qty;
    }
}
