package com.example.restapi.service;

import com.example.restapi.dto.ProductRequest;
import com.example.restapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(new ProductRepository());
    }

    @Test
    void createAndFindActive() {
        ProductRequest req = new ProductRequest();
        req.setTitle("Laptop");
        req.setDescription("15 inch");
        req.setUnitPrice(999.0);
        req.setActive(true);
        service.create(req);
        assertEquals(1, service.findActive().size());
    }
}
