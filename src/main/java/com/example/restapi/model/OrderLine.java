package com.example.restapi.model;

public class OrderLine {
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Double unitPrice;

    public OrderLine() {}

    public OrderLine(Long itemId, String itemName, Integer quantity, Double unitPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public double lineTotal() {
        return unitPrice * quantity;
    }
}
