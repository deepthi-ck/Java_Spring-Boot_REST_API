package com.example.restapi.mapper;

import com.example.restapi.dto.ItemRequest;
import com.example.restapi.dto.ItemResponse;
import com.example.restapi.model.Item;
import com.example.restapi.util.SkuUtils;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item toEntity(Long id, ItemRequest request) {
        return new Item(
                id,
                request.getName(),
                SkuUtils.normalize(request.getSku()),
                request.getPrice(),
                request.getStock(),
                request.getCategoryId()
        );
    }

    public ItemResponse toResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setSku(item.getSku());
        response.setPrice(item.getPrice());
        response.setStock(item.getStock());
        response.setCategoryId(item.getCategoryId());
        response.setCreatedAt(item.getCreatedAt());
        return response;
    }

    public void updateEntity(Item item, ItemRequest request) {
        item.setName(request.getName());
        item.setSku(SkuUtils.normalize(request.getSku()));
        item.setPrice(request.getPrice());
        item.setStock(request.getStock());
        item.setCategoryId(request.getCategoryId());
        item.setUpdatedAt(java.time.Instant.now());
    }
}
