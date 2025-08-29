package com.example;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegularEventListener {

    @EventListener
    public void handle(MyDomainEvent event) {
        System.out.println("Regular Listener received: " + event.getName());
    }
}
