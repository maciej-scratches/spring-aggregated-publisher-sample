package com.example.application;

import com.example.domain.ReservationCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegularEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularEventListener.class);

    @EventListener
    public void handle(ReservationCreated event) {
        LOGGER.info("Regular Listener received: {}", event);
    }
}
