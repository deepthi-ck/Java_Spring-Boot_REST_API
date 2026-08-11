package com.example.restapi.controller;

import com.example.restapi.audit.AuditAction;
import com.example.restapi.audit.AuditEntry;
import com.example.restapi.audit.AuditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public List<AuditEntry> recent(@RequestParam(defaultValue = "25") int limit) {
        return auditService.listRecent(limit);
    }

    @GetMapping("/actor")
    public List<AuditEntry> byActor(@RequestParam String actor) {
        return auditService.listByActor(actor);
    }

    @GetMapping("/mutations")
    public List<AuditEntry> mutations() {
        return auditService.listMutations();
    }

    @PostMapping
    public AuditEntry record(
            @RequestParam String action,
            @RequestParam String actor,
            @RequestParam String resourceType,
            @RequestParam(required = false) String resourceId,
            @RequestParam(required = false) String details) {
        return auditService.record(AuditAction.from(action), actor, resourceType, resourceId, details);
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> body = new HashMap<>();
        body.put("total", auditService.count());
        body.put("recent", auditService.listRecent(5));
        body.put("mutations", auditService.listMutations().size());
        return body;
    }
}
