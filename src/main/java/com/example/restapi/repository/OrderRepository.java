package com.example.restapi.repository;

import com.example.restapi.model.Order;
import com.example.restapi.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private final ConcurrentHashMap<Long, Order> store = new ConcurrentHashMap<>();
    private final IdGenerator ids = new IdGenerator(1);

    public List<Order> findAll() { return new ArrayList<>(store.values()); }

    public List<Order> findByCustomerId(Long customerId) {
        return store.values().stream()
                .filter(o -> o.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public Optional<Order> findById(Long id) { return Optional.ofNullable(store.get(id)); }

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(ids.nextId());
        }
        store.put(order.getId(), order);
        return order;
    }

    public long count() { return store.size(); }
}
