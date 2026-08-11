package com.example.restapi.controller;

import com.example.restapi.dto.OrderRequest;
import com.example.restapi.model.Order;
import com.example.restapi.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> list() { return orderService.findAll(); }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) { return orderService.findById(id); }

    @GetMapping("/customer/{customerId}")
    public List<Order> byCustomer(@PathVariable Long customerId) {
        return orderService.findByCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return orderService.updateStatus(id, body.get("status"));
    }
}
