package com.example.restapi.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationServiceTest {
    @Test
    void enqueuesAndDeliversByPriority() {
        NotificationService service = new NotificationService();
        service.enqueue(NotificationChannel.EMAIL, "a@x.com", "hi", "body");
        service.enqueue(NotificationChannel.SMS, "999", "alert", "urgent");
        assertEquals(2, service.pendingCount());
        assertTrue(service.deliverNext().isPresent());
        assertEquals(1, service.pendingCount());
        assertEquals(1, service.delivered().size());
    }
}
