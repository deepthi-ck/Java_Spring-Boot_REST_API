package com.example.restapi.controller;

import com.example.restapi.notification.NotificationChannel;
import com.example.restapi.notification.NotificationMessage;
import com.example.restapi.notification.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public NotificationMessage enqueue(
            @RequestParam String channel,
            @RequestParam String recipient,
            @RequestParam(required = false) String subject,
            @RequestParam String body) {
        return notificationService.enqueue(NotificationChannel.from(channel), recipient, subject, body);
    }

    @PostMapping("/deliver-next")
    public Map<String, Object> deliverNext() {
        Map<String, Object> body = new HashMap<>();
        body.put("delivered", notificationService.deliverNext().orElse(null));
        body.put("pending", notificationService.pendingCount());
        return body;
    }

    @GetMapping("/pending")
    public List<NotificationMessage> pending() {
        return notificationService.pending();
    }

    @GetMapping
    public List<NotificationMessage> latest(@RequestParam(defaultValue = "20") int limit) {
        return notificationService.latest(limit);
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> body = new HashMap<>();
        body.put("total", notificationService.totalCount());
        body.put("pending", notificationService.pendingCount());
        body.put("delivered", notificationService.delivered().size());
        return body;
    }
}
