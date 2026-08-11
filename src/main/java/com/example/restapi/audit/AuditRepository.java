package com.example.restapi.audit;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class AuditRepository {
    private final List<AuditEntry> entries = new CopyOnWriteArrayList<>();

    public AuditEntry save(AuditEntry entry) {
        entries.add(entry);
        return entry;
    }

    public List<AuditEntry> findAll() {
        return entries.stream()
                .sorted(Comparator.comparing(AuditEntry::getOccurredAt).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

    public List<AuditEntry> findByActor(String actor) {
        String needle = actor == null ? "" : actor.trim().toLowerCase(Locale.ROOT);
        return entries.stream()
                .filter(e -> e.getActor().toLowerCase(Locale.ROOT).equals(needle))
                .sorted(Comparator.comparing(AuditEntry::getOccurredAt).reversed())
                .toList();
    }

    public List<AuditEntry> findByAction(AuditAction action) {
        return entries.stream().filter(e -> e.getAction() == action).toList();
    }

    public Optional<AuditEntry> findById(String id) {
        return entries.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public int size() { return entries.size(); }

    public void clear() { entries.clear(); }

    public List<AuditEntry> recent(int limit) {
        int safe = Math.max(0, Math.min(limit, entries.size()));
        List<AuditEntry> all = new ArrayList<>(findAll());
        return List.copyOf(all.subList(0, safe));
    }
}
