package com.example.restapi.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CatalogServiceTest {
    @Test
    void createSearchAndDeactivate() {
        CatalogService service = new CatalogService();
        Brand brand = service.create("Acme Tools", "IN");
        assertEquals(1, service.search("acme").size());
        service.deactivate(brand.getId());
        assertFalse(service.listActive().contains(brand));
        assertEquals(1, service.count());
    }
}
