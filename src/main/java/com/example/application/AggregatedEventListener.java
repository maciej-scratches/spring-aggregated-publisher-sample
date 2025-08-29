package com.example.application;

import com.example.publisher.AggregatedDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AggregatedEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregatedEventListener.class);

    @EventListener
    public void handle(AggregatedDomainEvent event) {
        LOGGER.info("Aggregated Event received with {} events:", event.getEvents().size());
        for (Object e : event.getEvents()) {
            LOGGER.info(" - {}", e);
        }
    }

    @TransactionalEventListener
    public void handleAfterCommit(AggregatedDomainEvent event) {
        LOGGER.info("Aggregated Event received AFTER COMMIT with {} events:", event.getEvents().size());
        for (Object e : event.getEvents()) {
            LOGGER.info(" - {}", e);
        }
    }
}

