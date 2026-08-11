package com.example.restapi.controller;

import com.example.restapi.dto.ItemRequest;
import com.example.restapi.dto.ItemResponse;
import com.example.restapi.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ItemService itemService;

    @Test
    void listOk() throws Exception {
        ItemResponse item = new ItemResponse();
        item.setId(1L);
        item.setName("Book");
        item.setSku("BOOK-1");
        item.setPrice(10.0);
        item.setStock(2);
        when(itemService.findAll()).thenReturn(Collections.singletonList(item));
        mockMvc.perform(get("/api/items")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Book"));
    }

    @Test
    void createCreated() throws Exception {
        ItemRequest req = new ItemRequest();
        req.setName("Pen");
        req.setSku("PEN-1");
        req.setPrice(2.0);
        req.setStock(5);
        ItemResponse resp = new ItemResponse();
        resp.setId(1L);
        resp.setName("Pen");
        resp.setSku("PEN-1");
        when(itemService.create(any(ItemRequest.class))).thenReturn(resp);
        mockMvc.perform(post("/api/items").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
}
