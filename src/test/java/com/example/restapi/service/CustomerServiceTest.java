package com.example.restapi.service;

import com.example.restapi.dto.CustomerRequest;
import com.example.restapi.exception.ConflictException;
import com.example.restapi.mapper.CustomerMapper;
import com.example.restapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    private CustomerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerService(new CustomerRepository(), new CustomerMapper());
    }

    private CustomerRequest req(String name, String email) {
        CustomerRequest r = new CustomerRequest();
        r.setFullName(name);
        r.setEmail(email);
        r.setCity("NYC");
        return r;
    }

    @Test
    void createCustomer() {
        var c = service.create(req("Ada", "ada@example.com"));
        assertNotNull(c.getId());
        assertEquals(1, service.count());
    }

    @Test
    void duplicateEmailThrows() {
        service.create(req("Ada", "ada@example.com"));
        assertThrows(ConflictException.class, () -> service.create(req("Bob", "ada@example.com")));
    }
}
