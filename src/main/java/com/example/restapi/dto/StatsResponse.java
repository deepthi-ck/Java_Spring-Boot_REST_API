package com.example.restapi.dto;

public class StatsResponse {
    private long itemCount;
    private long orderCount;
    private long customerCount;
    private double inventoryValue;

    public StatsResponse() {}

    public StatsResponse(long itemCount, long orderCount, long customerCount, double inventoryValue) {
        this.itemCount = itemCount;
        this.orderCount = orderCount;
        this.customerCount = customerCount;
        this.inventoryValue = inventoryValue;
    }

    public long getItemCount() { return itemCount; }
    public void setItemCount(long itemCount) { this.itemCount = itemCount; }
    public long getOrderCount() { return orderCount; }
    public void setOrderCount(long orderCount) { this.orderCount = orderCount; }
    public long getCustomerCount() { return customerCount; }
    public void setCustomerCount(long customerCount) { this.customerCount = customerCount; }
    public double getInventoryValue() { return inventoryValue; }
    public void setInventoryValue(double inventoryValue) { this.inventoryValue = inventoryValue; }
}
