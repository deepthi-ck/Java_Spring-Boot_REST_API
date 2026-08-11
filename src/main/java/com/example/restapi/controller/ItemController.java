package com.example.restapi.controller;

import com.example.restapi.dto.ItemRequest;
import com.example.restapi.dto.ItemResponse;
import com.example.restapi.dto.StockAdjustRequest;
import com.example.restapi.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemResponse> list() { return itemService.findAll(); }

    @GetMapping("/{id}")
    public ItemResponse get(@PathVariable Long id) { return itemService.findById(id); }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@Valid @RequestBody ItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(request));
    }

    @PutMapping("/{id}")
    public ItemResponse update(@PathVariable Long id, @Valid @RequestBody ItemRequest request) {
        return itemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/stock")
    public ItemResponse adjustStock(@PathVariable Long id, @Valid @RequestBody StockAdjustRequest request) {
        return itemService.adjustStock(id, request);
    }
}
