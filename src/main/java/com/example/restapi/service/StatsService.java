package com.example.restapi.service;

import com.example.restapi.dto.StatsResponse;
import com.example.restapi.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private final ItemRepository itemRepository;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final ShipmentService shipmentService;

    public StatsService(ItemRepository itemRepository, OrderService orderService,
                        CustomerService customerService, ItemService itemService,
                        ShipmentService shipmentService) {
        this.itemRepository = itemRepository;
        this.orderService = orderService;
        this.customerService = customerService;
        this.itemService = itemService;
        this.shipmentService = shipmentService;
    }

    public StatsResponse summary() {
        return new StatsResponse(
                itemRepository.count(),
                orderService.count(),
                customerService.count(),
                shipmentService.count(),
                itemService.inventoryValue()
        );
    }
}
