package com.example.restapi.notification;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {
    private final List<NotificationMessage> inbox = new CopyOnWriteArrayList<>();

    public NotificationMessage enqueue(NotificationChannel channel, String recipient, String subject, String body) {
        NotificationMessage message = NotificationMessage.create(channel, recipient, subject, body);
        inbox.add(message);
        return message;
    }

    public Optional<NotificationMessage> deliverNext() {
        NotificationMessage next = inbox.stream()
                .filter(m -> !m.isDelivered())
                .sorted(Comparator.comparingInt(NotificationMessage::getPriority)
                        .thenComparing(NotificationMessage::getCreatedAt))
                .findFirst()
                .orElse(null);
        if (next == null) {
            return Optional.empty();
        }
        next.markDelivered();
        return Optional.of(next);
    }

    public List<NotificationMessage> pending() {
        return inbox.stream().filter(m -> !m.isDelivered()).toList();
    }

    public List<NotificationMessage> delivered() {
        return inbox.stream().filter(NotificationMessage::isDelivered).toList();
    }

    public List<NotificationMessage> byChannel(NotificationChannel channel) {
        Objects.requireNonNull(channel);
        return inbox.stream().filter(m -> m.getChannel() == channel).toList();
    }

    public int pendingCount() { return pending().size(); }

    public int totalCount() { return inbox.size(); }

    public List<NotificationMessage> latest(int limit) {
        List<NotificationMessage> copy = new ArrayList<>(inbox);
        copy.sort(Comparator.comparing(NotificationMessage::getCreatedAt).reversed());
        int safe = Math.max(0, Math.min(limit, copy.size()));
        return List.copyOf(copy.subList(0, safe));
    }
}
