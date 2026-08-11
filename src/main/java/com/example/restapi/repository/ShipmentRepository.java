package com.example.restapi.repository;

import com.example.restapi.model.Shipment;
import com.example.restapi.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ShipmentRepository {
    private final ConcurrentHashMap<Long, Shipment> store = new ConcurrentHashMap<>();
    private final IdGenerator ids = new IdGenerator(1);

    public List<Shipment> findAll() { return new ArrayList<>(store.values()); }
    public Optional<Shipment> findById(Long id) { return Optional.ofNullable(store.get(id)); }
    public List<Shipment> findByOrderId(Long orderId) {
        return store.values().stream().filter(s -> s.getOrderId().equals(orderId)).collect(Collectors.toList());
    }
    public Shipment save(Shipment shipment) {
        if (shipment.getId() == null) { shipment.setId(ids.nextId()); }
        store.put(shipment.getId(), shipment);
        return shipment;
    }
    public long count() { return store.size(); }
}
