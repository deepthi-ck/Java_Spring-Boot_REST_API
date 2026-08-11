package com.example.restapi.service;

import com.example.restapi.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ItemService {

    private final AtomicLong sequence = new AtomicLong(1);
    private final List<Item> items = new ArrayList<Item>();

    public List<Item> findAll() {
        return new ArrayList<Item>(items);
    }

    public Optional<Item> findById(Long id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public Item create(Item request) {
        Item saved = new Item(sequence.getAndIncrement(), request.getName(), request.getPrice());
        items.add(saved);
        return saved;
    }

    public Optional<Item> update(Long id, Item request) {
        Optional<Item> existing = findById(id);
        if (!existing.isPresent()) {
            return Optional.empty();
        }
        Item item = existing.get();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        return Optional.of(item);
    }

    public boolean delete(Long id) {
        Optional<Item> existing = findById(id);
        if (!existing.isPresent()) {
            return false;
        }
        return items.remove(existing.get());
    }

    public double totalValue() {
        double total = 0.0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }
}
