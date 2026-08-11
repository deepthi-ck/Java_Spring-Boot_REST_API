package com.example.restapi.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {
    @Test
    void fromParsesIgnoreCase() {
        assertEquals(OrderStatus.SHIPPED, OrderStatus.from("shipped"));
    }
}
