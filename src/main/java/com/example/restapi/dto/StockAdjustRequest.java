package com.example.restapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StockAdjustRequest {
    @NotNull
    private Integer delta;
    @NotBlank
    private String reason;

    public Integer getDelta() { return delta; }
    public void setDelta(Integer delta) { this.delta = delta; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
