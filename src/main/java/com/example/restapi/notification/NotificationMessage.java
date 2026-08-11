package com.example.restapi.notification;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class NotificationMessage {
    private final String id;
    private final NotificationChannel channel;
    private final String recipient;
    private final String subject;
    private final String body;
    private final int priority;
    private final Instant createdAt;
    private volatile boolean delivered;

    private NotificationMessage(String id, NotificationChannel channel, String recipient,
                                String subject, String body, int priority, Instant createdAt) {
        this.id = id;
        this.channel = channel;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.priority = priority;
        this.createdAt = createdAt;
        this.delivered = false;
    }

    public static NotificationMessage create(NotificationChannel channel, String recipient, String subject, String body) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(recipient, "recipient");
        int priority = channel.defaultPriority();
        return new NotificationMessage(UUID.randomUUID().toString(), channel, recipient.trim(),
                subject == null ? "" : subject.trim(), body == null ? "" : body, priority, Instant.now());
    }

    public String getId() { return id; }
    public NotificationChannel getChannel() { return channel; }
    public String getRecipient() { return recipient; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public int getPriority() { return priority; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isDelivered() { return delivered; }

    public void markDelivered() { this.delivered = true; }

    public String preview(int maxLen) {
        String text = body == null ? "" : body;
        int limit = Math.max(8, maxLen);
        return text.length() <= limit ? text : text.substring(0, limit) + "...";
    }

    @Override
    public String toString() {
        return "NotificationMessage{id='%s', channel=%s, recipient='%s', delivered=%s}".formatted(
                id, channel, recipient, delivered);
    }
}
