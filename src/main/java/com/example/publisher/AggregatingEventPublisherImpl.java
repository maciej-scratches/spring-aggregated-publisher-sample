package com.example.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class AggregatingEventPublisherImpl implements AggregatingEventPublisher {
    private final ApplicationEventPublisher delegate;
    private final AggregatingMulticaster aggregatingMulticaster;

    public AggregatingEventPublisherImpl(final ApplicationEventPublisher delegate, final AggregatingMulticaster aggregatingMulticaster) {
        this.delegate = delegate;
        this.aggregatingMulticaster = aggregatingMulticaster;
    }

    @Override
    public void publishEvent(final Object event) {
        delegate.publishEvent(event);
    }

    @Override
    public void withAggregatingEvents(final Runnable block) {
        aggregatingMulticaster.withAggregatingEvents(block);
    }
}
