package com.example.restapi.service;

import com.example.restapi.dto.ShipmentRequest;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.model.Shipment;
import com.example.restapi.repository.ShipmentRepository;
import com.example.restapi.util.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final OrderService orderService;

    public ShipmentService(ShipmentRepository shipmentRepository, OrderService orderService) {
        this.shipmentRepository = shipmentRepository;
        this.orderService = orderService;
    }

    public List<Shipment> findAll() { return shipmentRepository.findAll(); }

    public Shipment findById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found: " + id));
    }

    public List<Shipment> findByOrder(Long orderId) {
        orderService.findById(orderId);
        return shipmentRepository.findByOrderId(orderId);
    }

    public Shipment create(ShipmentRequest request) {
        orderService.findById(request.getOrderId());
        Shipment shipment = new Shipment(null, request.getOrderId(), request.getCarrier(),
                request.getTrackingNumber(), "IN_TRANSIT");
        Shipment saved = shipmentRepository.save(shipment);
        orderService.updateStatus(request.getOrderId(), OrderStatus.SHIPPED.name());
        return saved;
    }

    public long count() { return shipmentRepository.count(); }
}
