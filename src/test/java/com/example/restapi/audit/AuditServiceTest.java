package com.example.restapi.audit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditServiceTest {
    @Test
    void recordsAndListsEntries() {
        AuditService service = new AuditService(new AuditRepository());
        service.recordCreate("alice", "item", "1");
        service.recordUpdate("alice", "item", "1", "price");
        service.recordDelete("bob", "item", "2");
        assertEquals(3, service.count());
        assertEquals(2, service.listByActor("alice").size());
        assertTrue(service.listMutations().size() >= 3);
        assertEquals(2, service.listRecent(2).size());
    }
}
