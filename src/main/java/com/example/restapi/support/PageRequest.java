package com.example.restapi.support;

public final class PageRequest {
    private final int page;
    private final int size;
    private final SortOrder sortOrder;

    public PageRequest(int page, int size, SortOrder sortOrder) {
        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }
        if (size < 1 || size > 200) {
            throw new IllegalArgumentException("size must be 1..200");
        }
        this.page = page;
        this.size = size;
        this.sortOrder = sortOrder == null ? SortOrder.ASC : sortOrder;
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, SortOrder.ASC);
    }

    public int getPage() { return page; }
    public int getSize() { return size; }
    public SortOrder getSortOrder() { return sortOrder; }

    public int offset() { return page * size; }

    public int endExclusive(int total) {
        return Math.min(offset() + size, Math.max(total, 0));
    }

    public int totalPages(int total) {
        if (total <= 0) {
            return 0;
        }
        return (total + size - 1) / size;
    }

    public PageRequest next() {
        return new PageRequest(page + 1, size, sortOrder);
    }

    public PageRequest withSort(SortOrder order) {
        return new PageRequest(page, size, order);
    }
}
