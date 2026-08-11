package com.example.restapi.service;

import com.example.restapi.dto.ProductRequest;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.model.Product;
import com.example.restapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() { return productRepository.findAll(); }
    public List<Product> findActive() { return productRepository.findActive(); }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public Product create(ProductRequest request) {
        return productRepository.save(new Product(null, request.getTitle(), request.getDescription(),
                request.getUnitPrice(), request.isActive()));
    }

    public Product update(Long id, ProductRequest request) {
        Product product = findById(id);
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());
        product.setActive(request.isActive());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        if (!productRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
    }
}
