package com.example.restapi.model;

import java.time.Instant;

public class InventoryMovement {
    private Long id;
    private Long itemId;
    private int delta;
    private String reason;
    private Instant occurredAt;

    public InventoryMovement() {}

    public InventoryMovement(Long id, Long itemId, int delta, String reason) {
        this.id = id;
        this.itemId = itemId;
        this.delta = delta;
        this.reason = reason;
        this.occurredAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public int getDelta() { return delta; }
    public void setDelta(int delta) { this.delta = delta; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Instant getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Instant occurredAt) { this.occurredAt = occurredAt; }
}
