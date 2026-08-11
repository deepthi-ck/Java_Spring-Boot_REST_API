package com.example.restapi.service;

import com.example.restapi.dto.*;
import com.example.restapi.mapper.CustomerMapper;
import com.example.restapi.mapper.ItemMapper;
import com.example.restapi.repository.*;
import com.example.restapi.util.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentServiceTest {
    private ShipmentService shipmentService;
    private OrderService orderService;
    private ItemService itemService;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(new ItemRepository(), new InventoryRepository(), new ItemMapper());
        customerService = new CustomerService(new CustomerRepository(), new CustomerMapper());
        orderService = new OrderService(new OrderRepository(), itemService, customerService, new PricingService(0.0, 0.0));
        shipmentService = new ShipmentService(new ShipmentRepository(), orderService);
    }

    @Test
    void createShipmentMarksOrderShipped() {
        ItemRequest itemReq = new ItemRequest();
        itemReq.setName("Book"); itemReq.setSku("BOOK-SHIP"); itemReq.setPrice(10.0); itemReq.setStock(5);
        var item = itemService.create(itemReq);
        CustomerRequest custReq = new CustomerRequest();
        custReq.setFullName("Ada"); custReq.setEmail("ship@example.com");
        var customer = customerService.create(custReq);
        OrderLineRequest line = new OrderLineRequest();
        line.setItemId(item.getId()); line.setQuantity(1);
        OrderRequest orderReq = new OrderRequest();
        orderReq.setCustomerId(customer.getId());
        orderReq.setLines(List.of(line));
        var order = orderService.create(orderReq);

        ShipmentRequest shipReq = new ShipmentRequest();
        shipReq.setOrderId(order.getId());
        shipReq.setCarrier("UPS");
        shipReq.setTrackingNumber("1Z999");
        var shipment = shipmentService.create(shipReq);
        assertEquals("IN_TRANSIT", shipment.getStatus());
        assertEquals(OrderStatus.SHIPPED.name(), orderService.findById(order.getId()).getStatus());
    }
}
