package com.example.publisher;

import java.util.List;

public class AggregatedDomainEvent {
    private final List<Object> events;

    public AggregatedDomainEvent(List<Object> events) {
        this.events = events;
    }

    public List<Object> getEvents() {
        return events;
    }
}