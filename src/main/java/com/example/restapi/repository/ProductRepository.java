package com.example.restapi.repository;

import com.example.restapi.model.Product;
import com.example.restapi.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private final ConcurrentHashMap<Long, Product> store = new ConcurrentHashMap<>();
    private final IdGenerator ids = new IdGenerator(1);

    public List<Product> findAll() { return new ArrayList<>(store.values()); }
    public List<Product> findActive() {
        return store.values().stream().filter(Product::isActive).collect(Collectors.toList());
    }
    public Optional<Product> findById(Long id) { return Optional.ofNullable(store.get(id)); }
    public Product save(Product product) {
        if (product.getId() == null) { product.setId(ids.nextId()); }
        store.put(product.getId(), product);
        return product;
    }
    public boolean deleteById(Long id) { return store.remove(id) != null; }
}
