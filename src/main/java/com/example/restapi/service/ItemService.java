package com.example.restapi.service;

import com.example.restapi.dto.ItemRequest;
import com.example.restapi.dto.ItemResponse;
import com.example.restapi.dto.StockAdjustRequest;
import com.example.restapi.exception.BadRequestException;
import com.example.restapi.exception.ConflictException;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.mapper.ItemMapper;
import com.example.restapi.model.InventoryMovement;
import com.example.restapi.model.Item;
import com.example.restapi.repository.InventoryRepository;
import com.example.restapi.repository.ItemRepository;
import com.example.restapi.util.SkuUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, InventoryRepository inventoryRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.inventoryRepository = inventoryRepository;
        this.itemMapper = itemMapper;
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll().stream().map(itemMapper::toResponse).collect(Collectors.toList());
    }

    public ItemResponse findById(Long id) { return itemMapper.toResponse(requireItem(id)); }

    public ItemResponse create(ItemRequest request) {
        if (!SkuUtils.isValid(request.getSku())) {
            throw new BadRequestException("Invalid SKU format");
        }
        String sku = SkuUtils.normalize(request.getSku());
        itemRepository.findBySku(sku).ifPresent(existing -> {
            throw new ConflictException("SKU already exists: " + sku);
        });
        return itemMapper.toResponse(itemRepository.save(itemMapper.toEntity(null, request)));
    }

    public ItemResponse update(Long id, ItemRequest request) {
        Item item = requireItem(id);
        if (!SkuUtils.isValid(request.getSku())) {
            throw new BadRequestException("Invalid SKU format");
        }
        String sku = SkuUtils.normalize(request.getSku());
        itemRepository.findBySku(sku).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException("SKU already exists: " + sku);
            }
        });
        itemMapper.updateEntity(item, request);
        return itemMapper.toResponse(itemRepository.save(item));
    }

    public void delete(Long id) {
        if (!itemRepository.deleteById(id)) {
            throw new ResourceNotFoundException("Item not found: " + id);
        }
    }

    public ItemResponse adjustStock(Long id, StockAdjustRequest request) {
        Item item = requireItem(id);
        int next = item.getStock() + request.getDelta();
        if (next < 0) {
            throw new BadRequestException("Stock cannot be negative");
        }
        item.setStock(next);
        itemRepository.save(item);
        inventoryRepository.save(new InventoryMovement(null, id, request.getDelta(), request.getReason()));
        return itemMapper.toResponse(item);
    }

    public double inventoryValue() {
        return itemRepository.findAll().stream().mapToDouble(i -> i.getPrice() * i.getStock()).sum();
    }

    public Item requireItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + id));
    }
}
