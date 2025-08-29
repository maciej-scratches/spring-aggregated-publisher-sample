package com.example;

import com.example.publisher.AggregatedDomainEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AggregatedEventListener {

    @EventListener
    public void handle(AggregatedDomainEvent event) {
        System.out.println("Aggregated Event received with " + event.getEvents().size() + " events:");
        for (Object e : event.getEvents()) {
            System.out.println(" - " + e);
        }
    }
}

