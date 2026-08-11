package com.example.restapi.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientContextTest {
    @Test
    void buildsContexts() {
        ClientContext ctx = ClientContext.of("c1", "en-IN", "mobile");
        assertTrue(ctx.isMobile());
        assertEquals("c1", ctx.getClientId());
        assertTrue(ctx.asInternal().isInternal());
        assertEquals("anonymous", ClientContext.anonymous().getClientId());
    }
}
