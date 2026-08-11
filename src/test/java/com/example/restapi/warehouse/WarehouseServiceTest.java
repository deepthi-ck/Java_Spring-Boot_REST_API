package com.example.restapi.warehouse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WarehouseServiceTest {
    @Test
    void reserveAndShip() {
        WarehouseService service = new WarehouseService();
        service.register("WH1", "Bengaluru", 100);
        service.upsertStock("SKU-1", "WH1", 50);
        service.reserve("SKU-1", "WH1", 5);
        StockLevel shipped = service.ship("SKU-1", "WH1", 5);
        assertEquals(45, shipped.getOnHand());
        assertEquals(0, shipped.getReserved());
        assertEquals(1, service.warehouseCount());
    }
}
