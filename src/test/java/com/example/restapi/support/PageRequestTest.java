package com.example.restapi.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageRequestTest {
    @Test
    void computesOffsetsAndPages() {
        PageRequest page = PageRequest.of(1, 10).withSort(SortOrder.DESC);
        assertEquals(10, page.offset());
        assertEquals(20, page.endExclusive(25));
        assertEquals(3, page.totalPages(25));
        assertTrue(page.next().getPage() == 2);
        assertEquals(SortOrder.ASC, SortOrder.DESC.opposite());
        assertTrue(Result.ok("x").isSuccess());
        assertTrue(Result.fail("e").error().isPresent());
    }
}
