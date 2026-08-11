package com.example.restapi.audit;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuditService {
    private final AuditRepository repository;

    public AuditService(AuditRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public AuditEntry record(AuditAction action, String actor, String resourceType, String resourceId, String details) {
        return repository.save(AuditEntry.of(action, actor, resourceType, resourceId, details));
    }

    public AuditEntry recordCreate(String actor, String resourceType, String resourceId) {
        return record(AuditAction.CREATE, actor, resourceType, resourceId, "created");
    }

    public AuditEntry recordUpdate(String actor, String resourceType, String resourceId, String details) {
        return record(AuditAction.UPDATE, actor, resourceType, resourceId, details);
    }

    public AuditEntry recordDelete(String actor, String resourceType, String resourceId) {
        return record(AuditAction.DELETE, actor, resourceType, resourceId, "deleted");
    }

    public List<AuditEntry> listRecent(int limit) {
        return repository.recent(limit <= 0 ? 20 : limit);
    }

    public List<AuditEntry> listByActor(String actor) {
        return repository.findByActor(actor);
    }

    public List<AuditEntry> listMutations() {
        return repository.findAll().stream().filter(e -> e.getAction().isMutating()).toList();
    }

    public int count() {
        return repository.size();
    }
}
