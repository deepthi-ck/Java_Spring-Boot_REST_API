package com.example.restapi.service;

import com.example.restapi.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemServiceTest {

    private ItemService service;

    @BeforeEach
    void setUp() {
        service = new ItemService();
    }

    @Test
    void createAndFindById() {
        Item created = service.create(new Item(null, "Book", 12.5));
        Optional<Item> found = service.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Book", found.get().getName());
        assertEquals(12.5, found.get().getPrice(), 0.0001);
    }

    @Test
    void updateExistingItem() {
        Item created = service.create(new Item(null, "Pen", 2.0));
        Optional<Item> updated = service.update(created.getId(), new Item(null, "Marker", 3.5));
        assertTrue(updated.isPresent());
        assertEquals("Marker", updated.get().getName());
        assertEquals(3.5, updated.get().getPrice(), 0.0001);
    }

    @Test
    void updateMissingItemReturnsEmpty() {
        Optional<Item> updated = service.update(99L, new Item(null, "X", 1.0));
        assertFalse(updated.isPresent());
    }

    @Test
    void deleteExistingItem() {
        Item created = service.create(new Item(null, "Bag", 5.0));
        assertTrue(service.delete(created.getId()));
        assertFalse(service.findById(created.getId()).isPresent());
    }

    @Test
    void totalValueSumsPrices() {
        service.create(new Item(null, "A", 10.0));
        service.create(new Item(null, "B", 5.5));
        assertEquals(15.5, service.totalValue(), 0.0001);
    }
}
