package com.example.restapi.dto;

public class StatsResponse {
    private long itemCount;
    private long orderCount;
    private long customerCount;
    private long shipmentCount;
    private double inventoryValue;

    public StatsResponse() {}

    public StatsResponse(long itemCount, long orderCount, long customerCount, long shipmentCount, double inventoryValue) {
        this.itemCount = itemCount;
        this.orderCount = orderCount;
        this.customerCount = customerCount;
        this.shipmentCount = shipmentCount;
        this.inventoryValue = inventoryValue;
    }

    public long getItemCount() { return itemCount; }
    public void setItemCount(long itemCount) { this.itemCount = itemCount; }
    public long getOrderCount() { return orderCount; }
    public void setOrderCount(long orderCount) { this.orderCount = orderCount; }
    public long getCustomerCount() { return customerCount; }
    public void setCustomerCount(long customerCount) { this.customerCount = customerCount; }
    public long getShipmentCount() { return shipmentCount; }
    public void setShipmentCount(long shipmentCount) { this.shipmentCount = shipmentCount; }
    public double getInventoryValue() { return inventoryValue; }
    public void setInventoryValue(double inventoryValue) { this.inventoryValue = inventoryValue; }
}
