package com.example.restapi.event;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventPublisherTest {

    @Test
    void publishAndDrain() {
        EventPublisher publisher = new EventPublisher(10);
        publisher.publish(DomainEvent.of("created", "item-1"));
        publisher.publish(DomainEvent.of("updated", "item-1"));
        assertEquals(2, publisher.size());
        assertFalse(publisher.isEmpty());

        List<DomainEvent> drained = publisher.drain();
        assertEquals(2, drained.size());
        assertTrue(publisher.isEmpty());
    }

    @Test
    void findAndClearByType() {
        EventPublisher publisher = new EventPublisher(20);
        publisher.publish(DomainEvent.of("order", "a"));
        publisher.publish(DomainEvent.of("shipment", "b"));
        publisher.publish(DomainEvent.of("order", "c"));

        assertEquals(2, publisher.findByType("order").size());
        assertEquals(2, publisher.clearByType("order"));
        assertEquals(1, publisher.size());
        assertEquals(1, publisher.peek(5).size());
    }

    @Test
    void dropsOldestWhenFull() {
        EventPublisher publisher = new EventPublisher(2);
        publisher.publish(DomainEvent.of("a", "1"));
        publisher.publish(DomainEvent.of("b", "2"));
        publisher.publish(DomainEvent.of("c", "3"));
        assertTrue(publisher.isFull());
        assertEquals(2, publisher.size());
        assertEquals("b", publisher.peek(1).get(0).getType());
    }
}
