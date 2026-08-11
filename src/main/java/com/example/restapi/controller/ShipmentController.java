package com.example.restapi.controller;

import com.example.restapi.dto.ShipmentRequest;
import com.example.restapi.model.Shipment;
import com.example.restapi.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) { this.shipmentService = shipmentService; }

    @GetMapping
    public List<Shipment> list() { return shipmentService.findAll(); }

    @GetMapping("/{id}")
    public Shipment get(@PathVariable Long id) { return shipmentService.findById(id); }

    @GetMapping("/order/{orderId}")
    public List<Shipment> byOrder(@PathVariable Long orderId) {
        return shipmentService.findByOrder(orderId);
    }

    @PostMapping
    public ResponseEntity<Shipment> create(@Valid @RequestBody ShipmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.create(request));
    }
}
