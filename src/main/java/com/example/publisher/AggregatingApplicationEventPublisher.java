package com.example.publisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
@Primary
public class AggregatingApplicationEventPublisher implements ApplicationEventPublisher {

    private final ApplicationEventPublisher delegate;

    private static final ThreadLocal<List<Object>> eventBuffer = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> isAggregating = ThreadLocal.withInitial(() -> false);

    public AggregatingApplicationEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publishEvent(Object event) {
        if (Boolean.TRUE.equals(isAggregating.get())) {
            eventBuffer.get().add(event);
        } else {
            delegate.publishEvent(event);
        }
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        publishEvent((Object) event);
    }

    public void withAggregatingEvents(Runnable runnable) {
        try {
            isAggregating.set(true);
            eventBuffer.set(new ArrayList<>());
            runnable.run();
            List<Object> events = eventBuffer.get();
            if (!events.isEmpty()) {
                delegate.publishEvent(new AggregatedDomainEvent(events));
            }
        } finally {
            isAggregating.remove();
            eventBuffer.remove();
        }
    }

    public <T> T withAggregatingEvents(Supplier<T> supplier) {
        try {
            isAggregating.set(true);
            eventBuffer.set(new ArrayList<>());
            T result = supplier.get();
            List<Object> events = eventBuffer.get();
            if (!events.isEmpty()) {
                delegate.publishEvent(new AggregatedDomainEvent(events));
            }
            return result;
        } finally {
            isAggregating.remove();
            eventBuffer.remove();
        }
    }
}

