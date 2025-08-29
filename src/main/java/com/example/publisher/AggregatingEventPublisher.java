package com.example.publisher;

import org.springframework.context.ApplicationEventPublisher;

public interface AggregatingEventPublisher extends ApplicationEventPublisher {
    void withAggregatingEvents(Runnable block);
}
