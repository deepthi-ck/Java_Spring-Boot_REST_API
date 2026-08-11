package com.example.restapi.warehouse;

import java.util.Objects;
import java.util.UUID;

public class Warehouse {
    private final String id;
    private final String code;
    private final String city;
    private final int capacity;
    private int occupied;

    public Warehouse(String code, String city, int capacity) {
        this.id = UUID.randomUUID().toString();
        this.code = Objects.requireNonNull(code).trim().toUpperCase();
        this.city = Objects.requireNonNull(city).trim();
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        this.capacity = capacity;
        this.occupied = 0;
    }

    public String getId() { return id; }
    public String getCode() { return code; }
    public String getCity() { return city; }
    public int getCapacity() { return capacity; }
    public int getOccupied() { return occupied; }

    public int available() { return capacity - occupied; }

    public boolean canAllocate(int units) {
        return units > 0 && units <= available();
    }

    public void allocate(int units) {
        if (!canAllocate(units)) {
            throw new IllegalStateException("insufficient capacity in " + code);
        }
        occupied += units;
    }

    public void release(int units) {
        if (units < 0 || units > occupied) {
            throw new IllegalArgumentException("invalid release units");
        }
        occupied -= units;
    }

    public double utilization() {
        return (occupied * 100.0) / capacity;
    }
}
