package com.example.restapi.service;

import com.example.restapi.dto.ItemRequest;
import com.example.restapi.dto.StockAdjustRequest;
import com.example.restapi.exception.BadRequestException;
import com.example.restapi.exception.ConflictException;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.mapper.ItemMapper;
import com.example.restapi.repository.InventoryRepository;
import com.example.restapi.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {
    private ItemService service;

    @BeforeEach
    void setUp() {
        service = new ItemService(new ItemRepository(), new InventoryRepository(), new ItemMapper());
    }

    private ItemRequest req(String name, String sku, double price, int stock) {
        ItemRequest r = new ItemRequest();
        r.setName(name);
        r.setSku(sku);
        r.setPrice(price);
        r.setStock(stock);
        return r;
    }

    @Test
    void createAndFind() {
        var created = service.create(req("Book", "BOOK-1", 12.5, 5));
        assertNotNull(created.getId());
        assertEquals("BOOK-1", created.getSku());
        assertEquals("Book", service.findById(created.getId()).getName());
    }

    @Test
    void duplicateSkuThrows() {
        service.create(req("A", "SKU-1", 1.0, 1));
        assertThrows(ConflictException.class, () -> service.create(req("B", "sku-1", 2.0, 1)));
    }

    @Test
    void adjustStock() {
        var created = service.create(req("Pen", "PEN-1", 2.0, 10));
        StockAdjustRequest adj = new StockAdjustRequest();
        adj.setDelta(-3);
        adj.setReason("sale");
        assertEquals(7, service.adjustStock(created.getId(), adj).getStock());
    }

    @Test
    void adjustStockNegativeThrows() {
        var created = service.create(req("Pen", "PEN-2", 2.0, 1));
        StockAdjustRequest adj = new StockAdjustRequest();
        adj.setDelta(-5);
        adj.setReason("bad");
        assertThrows(BadRequestException.class, () -> service.adjustStock(created.getId(), adj));
    }

    @Test
    void missingItemThrows() {
        assertThrows(ResourceNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    void inventoryValue() {
        service.create(req("A", "A-1", 10.0, 2));
        service.create(req("B", "B-1", 5.0, 3));
        assertEquals(35.0, service.inventoryValue(), 0.0001);
    }
}
