package com.example.restapi.repository;

import com.example.restapi.model.Item;
import com.example.restapi.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {
    private final ConcurrentHashMap<Long, Item> store = new ConcurrentHashMap<>();
    private final IdGenerator ids = new IdGenerator(1);

    public List<Item> findAll() { return new ArrayList<>(store.values()); }
    public Optional<Item> findById(Long id) { return Optional.ofNullable(store.get(id)); }
    public Optional<Item> findBySku(String sku) {
        return store.values().stream().filter(i -> i.getSku().equals(sku)).findFirst();
    }
    public Item save(Item item) {
        if (item.getId() == null) { item.setId(ids.nextId()); }
        store.put(item.getId(), item);
        return item;
    }
    public boolean deleteById(Long id) { return store.remove(id) != null; }
    public long count() { return store.size(); }
}
