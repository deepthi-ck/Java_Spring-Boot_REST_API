package com.example.restapi.audit;

import java.util.Locale;

public enum AuditAction {
    CREATE, UPDATE, DELETE, READ, LOGIN, LOGOUT, EXPORT, IMPORT, ADJUST, PAY;

    public static AuditAction from(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("action required");
        }
        return AuditAction.valueOf(raw.trim().toUpperCase(Locale.ROOT));
    }

    public boolean isMutating() {
        return this == CREATE || this == UPDATE || this == DELETE || this == ADJUST || this == PAY || this == IMPORT;
    }

    public boolean isSecurityRelated() {
        return this == LOGIN || this == LOGOUT;
    }

    public String label() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT);
    }
}
