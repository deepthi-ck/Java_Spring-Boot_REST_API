package com.example.restapi.model;

public class Product {
    private Long id;
    private String title;
    private String description;
    private Double unitPrice;
    private boolean active;

    public Product() {}

    public Product(Long id, String title, String description, Double unitPrice, boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.unitPrice = unitPrice;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
