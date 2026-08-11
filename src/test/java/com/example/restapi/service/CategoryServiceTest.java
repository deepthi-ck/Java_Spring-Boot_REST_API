package com.example.restapi.service;

import com.example.restapi.dto.CategoryRequest;
import com.example.restapi.exception.ConflictException;
import com.example.restapi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {
    private CategoryService service;

    @BeforeEach
    void setUp() { service = new CategoryService(new CategoryRepository()); }

    @Test
    void createAndList() {
        CategoryRequest req = new CategoryRequest();
        req.setName("Books"); req.setCode("BOOKS");
        service.create(req);
        assertEquals(1, service.findAll().size());
    }

    @Test
    void duplicateCodeThrows() {
        CategoryRequest req = new CategoryRequest();
        req.setName("Books"); req.setCode("BOOKS");
        service.create(req);
        assertThrows(ConflictException.class, () -> service.create(req));
    }
}
