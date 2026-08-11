package com.example.restapi.service;

import com.example.restapi.dto.CustomerRequest;
import com.example.restapi.dto.ItemRequest;
import com.example.restapi.dto.OrderLineRequest;
import com.example.restapi.dto.OrderRequest;
import com.example.restapi.mapper.CustomerMapper;
import com.example.restapi.mapper.ItemMapper;
import com.example.restapi.repository.*;
import com.example.restapi.util.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService orderService;
    private ItemService itemService;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(new ItemRepository(), new InventoryRepository(), new ItemMapper());
        customerService = new CustomerService(new CustomerRepository(), new CustomerMapper());
        orderService = new OrderService(new OrderRepository(), itemService, customerService, new PricingService(0.0, 0.0));
    }

    @Test
    void createOrderReducesStock() {
        ItemRequest itemReq = new ItemRequest();
        itemReq.setName("Book");
        itemReq.setSku("BOOK-9");
        itemReq.setPrice(10.0);
        itemReq.setStock(5);
        com.example.restapi.dto.ItemResponse item = itemService.create(itemReq);

        CustomerRequest custReq = new CustomerRequest();
        custReq.setFullName("Ada");
        custReq.setEmail("ada2@example.com");
        com.example.restapi.model.Customer customer = customerService.create(custReq);

        OrderLineRequest line = new OrderLineRequest();
        line.setItemId(item.getId());
        line.setQuantity(2);
        OrderRequest orderReq = new OrderRequest();
        orderReq.setCustomerId(customer.getId());
        orderReq.setLines(Collections.singletonList(line));

        com.example.restapi.model.Order order = orderService.create(orderReq);
        assertEquals(OrderStatus.NEW.name(), order.getStatus());
        assertEquals(20.0, order.getTotalAmount(), 0.0001);
        assertEquals(3, itemService.findById(item.getId()).getStock());
    }
}
