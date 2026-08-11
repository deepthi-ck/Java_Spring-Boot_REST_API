package com.example.restapi.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderLineRequest {
    @NotNull
    private Long itemId;
    @NotNull @Positive
    private Integer quantity;

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
