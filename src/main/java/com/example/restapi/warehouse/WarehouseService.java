package com.example.restapi.warehouse;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WarehouseService {
    private final Map<String, Warehouse> warehouses = new ConcurrentHashMap<>();
    private final Map<String, StockLevel> stock = new ConcurrentHashMap<>();

    public Warehouse register(String code, String city, int capacity) {
        Warehouse warehouse = new Warehouse(code, city, capacity);
        warehouses.put(warehouse.getCode(), warehouse);
        return warehouse;
    }

    public Optional<Warehouse> find(String code) {
        return Optional.ofNullable(warehouses.get(normalize(code)));
    }

    public StockLevel upsertStock(String sku, String warehouseCode, int onHand) {
        String key = key(sku, warehouseCode);
        StockLevel level = new StockLevel(sku, warehouseCode, onHand);
        stock.put(key, level);
        return level;
    }

    public StockLevel reserve(String sku, String warehouseCode, int qty) {
        StockLevel level = requireStock(sku, warehouseCode);
        Warehouse warehouse = requireWarehouse(warehouseCode);
        warehouse.allocate(qty);
        level.reserve(qty);
        return level;
    }

    public StockLevel ship(String sku, String warehouseCode, int qty) {
        StockLevel level = requireStock(sku, warehouseCode);
        Warehouse warehouse = requireWarehouse(warehouseCode);
        level.ship(qty);
        warehouse.release(qty);
        return level;
    }

    public List<Warehouse> listWarehouses() {
        return List.copyOf(new ArrayList<>(warehouses.values()));
    }

    public List<StockLevel> listStock() {
        return List.copyOf(new ArrayList<>(stock.values()));
    }

    public int warehouseCount() { return warehouses.size(); }

    public int stockCount() { return stock.size(); }

    private Warehouse requireWarehouse(String code) {
        return find(code).orElseThrow(() -> new IllegalArgumentException("warehouse not found: " + code));
    }

    private StockLevel requireStock(String sku, String warehouseCode) {
        StockLevel level = stock.get(key(sku, warehouseCode));
        if (level == null) {
            throw new IllegalArgumentException("stock not found");
        }
        return level;
    }

    private static String normalize(String code) {
        return code == null ? "" : code.trim().toUpperCase(Locale.ROOT);
    }

    private static String key(String sku, String warehouseCode) {
        return normalize(sku) + "@" + normalize(warehouseCode);
    }
}
