package com.example.restapi.controller;

import com.example.restapi.catalog.Brand;
import com.example.restapi.catalog.CatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/catalog/brands")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping
    public Brand create(@RequestParam String name, @RequestParam(required = false) String country) {
        return catalogService.create(name, country);
    }

    @GetMapping
    public List<Brand> list(@RequestParam(required = false) String q) {
        return q == null || q.isBlank() ? catalogService.listActive() : catalogService.search(q);
    }

    @GetMapping("/{id}")
    public Brand get(@PathVariable String id) {
        return catalogService.find(id).orElseThrow(() -> new IllegalArgumentException("not found"));
    }

    @PostMapping("/{id}/deactivate")
    public Brand deactivate(@PathVariable String id) {
        return catalogService.deactivate(id);
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> body = new HashMap<>();
        body.put("total", catalogService.count());
        body.put("active", catalogService.listActive().size());
        return body;
    }
}
