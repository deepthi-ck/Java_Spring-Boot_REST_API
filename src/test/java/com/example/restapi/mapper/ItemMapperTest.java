package com.example.restapi.mapper;

import com.example.restapi.dto.ItemRequest;
import com.example.restapi.model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {
    private final ItemMapper mapper = new ItemMapper();

    @Test
    void toEntityNormalizesSku() {
        ItemRequest req = new ItemRequest();
        req.setName("Book");
        req.setSku("abc-1");
        req.setPrice(1.0);
        req.setStock(1);
        Item item = mapper.toEntity(10L, req);
        assertEquals(10L, item.getId());
        assertEquals("ABC-1", item.getSku());
    }
}
