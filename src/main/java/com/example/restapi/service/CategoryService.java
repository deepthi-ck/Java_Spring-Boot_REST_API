package com.example.restapi.service;

import com.example.restapi.dto.CategoryRequest;
import com.example.restapi.exception.ConflictException;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.model.Category;
import com.example.restapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() { return categoryRepository.findAll(); }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    public Category create(CategoryRequest request) {
        categoryRepository.findByCode(request.getCode()).ifPresent(c -> {
            throw new ConflictException("Category code exists: " + request.getCode());
        });
        return categoryRepository.save(new Category(null, request.getName(), request.getCode()));
    }

    public Category update(Long id, CategoryRequest request) {
        Category category = findById(id);
        categoryRepository.findByCode(request.getCode()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException("Category code exists: " + request.getCode());
            }
        });
        category.setName(request.getName());
        category.setCode(request.getCode());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Category not found: " + id);
        }
    }
}
