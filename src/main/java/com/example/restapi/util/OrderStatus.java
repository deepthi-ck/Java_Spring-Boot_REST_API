package com.example.restapi.util;

public enum OrderStatus {
    NEW, CONFIRMED, SHIPPED, CANCELLED;

    public static OrderStatus from(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }
}
