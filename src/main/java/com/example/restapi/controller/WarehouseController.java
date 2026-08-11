package com.example.restapi.controller;

import com.example.restapi.warehouse.StockLevel;
import com.example.restapi.warehouse.Warehouse;
import com.example.restapi.warehouse.WarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public Warehouse register(@RequestParam String code, @RequestParam String city, @RequestParam int capacity) {
        return warehouseService.register(code, city, capacity);
    }

    @GetMapping
    public List<Warehouse> list() {
        return warehouseService.listWarehouses();
    }

    @PostMapping("/stock")
    public StockLevel upsertStock(@RequestParam String sku, @RequestParam String warehouseCode, @RequestParam int onHand) {
        return warehouseService.upsertStock(sku, warehouseCode, onHand);
    }

    @PostMapping("/reserve")
    public StockLevel reserve(@RequestParam String sku, @RequestParam String warehouseCode, @RequestParam int qty) {
        return warehouseService.reserve(sku, warehouseCode, qty);
    }

    @PostMapping("/ship")
    public StockLevel ship(@RequestParam String sku, @RequestParam String warehouseCode, @RequestParam int qty) {
        return warehouseService.ship(sku, warehouseCode, qty);
    }

    @GetMapping("/stock")
    public List<StockLevel> stock() {
        return warehouseService.listStock();
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> body = new HashMap<>();
        body.put("warehouses", warehouseService.warehouseCount());
        body.put("stockRows", warehouseService.stockCount());
        return body;
    }
}
