package com.example.restapi.repository;

import com.example.restapi.model.Category;
import com.example.restapi.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CategoryRepository {
    private final ConcurrentHashMap<Long, Category> store = new ConcurrentHashMap<>();
    private final IdGenerator ids = new IdGenerator(1);

    public List<Category> findAll() { return new ArrayList<>(store.values()); }

    public Optional<Category> findById(Long id) { return Optional.ofNullable(store.get(id)); }

    public Optional<Category> findByCode(String code) {
        return store.values().stream().filter(c -> c.getCode().equalsIgnoreCase(code)).findFirst();
    }

    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(ids.nextId());
        }
        store.put(category.getId(), category);
        return category;
    }

    public boolean deleteById(Long id) { return store.remove(id) != null; }
}
