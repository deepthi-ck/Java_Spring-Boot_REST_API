package com.example.restapi.web;

import java.util.Locale;
import java.util.Objects;

public final class ClientContext {
    private final String clientId;
    private final String locale;
    private final String channel;
    private final boolean internal;

    public ClientContext(String clientId, String locale, String channel, boolean internal) {
        this.clientId = clientId == null || clientId.isBlank() ? "anonymous" : clientId.trim();
        this.locale = locale == null || locale.isBlank() ? Locale.US.toLanguageTag() : locale.trim();
        this.channel = channel == null || channel.isBlank() ? "web" : channel.trim().toLowerCase(Locale.ROOT);
        this.internal = internal;
    }

    public static ClientContext anonymous() {
        return new ClientContext("anonymous", Locale.US.toLanguageTag(), "web", false);
    }

    public static ClientContext of(String clientId, String locale, String channel) {
        return new ClientContext(clientId, locale, channel, false);
    }

    public String getClientId() { return clientId; }
    public String getLocale() { return locale; }
    public String getChannel() { return channel; }
    public boolean isInternal() { return internal; }

    public ClientContext asInternal() {
        return new ClientContext(clientId, locale, channel, true);
    }

    public boolean isMobile() {
        return "mobile".equals(channel) || "android".equals(channel) || "ios".equals(channel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientContext that)) return false;
        return internal == that.internal
                && clientId.equals(that.clientId)
                && locale.equals(that.locale)
                && channel.equals(that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, locale, channel, internal);
    }

    @Override
    public String toString() {
        return "ClientContext{clientId='%s', locale='%s', channel='%s', internal=%s}".formatted(
                clientId, locale, channel, internal);
    }
}
