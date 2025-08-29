package com.example.publisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class AggregatingMulticaster implements ApplicationEventMulticaster {

    private final ApplicationEventMulticaster delegate;
    private static final ThreadLocal<List<Object>> eventBuffer = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<Boolean> isAggregating = ThreadLocal.withInitial(() -> false);

    public AggregatingMulticaster(ApplicationEventMulticaster delegate) {
        this.delegate = delegate;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        delegate.addApplicationListener(listener);
    }

    @Override
    public void addApplicationListenerBean(String listenerBeanName) {
        delegate.addApplicationListenerBean(listenerBeanName);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        delegate.removeApplicationListener(listener);
    }

    @Override
    public void removeApplicationListenerBean(String listenerBeanName) {
        delegate.removeApplicationListenerBean(listenerBeanName);
    }

    @Override
    public void removeApplicationListeners(final Predicate<ApplicationListener<?>> predicate) {
        delegate.removeApplicationListeners(predicate);
    }

    @Override
    public void removeApplicationListenerBeans(final Predicate<String> predicate) {
        delegate.removeApplicationListenerBeans(predicate);
    }

    @Override
    public void removeAllListeners() {
        delegate.removeAllListeners();
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        multicastEvent(event, null);
    }

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        if (Boolean.TRUE.equals(isAggregating.get())) {
            if (event instanceof PayloadApplicationEvent payloadApplicationEvent) {
                eventBuffer.get().add(payloadApplicationEvent.getPayload());
            } else {
                eventBuffer.get().add(event);
            }
        } else {
            delegate.multicastEvent(event, eventType);
        }
    }

    public void withAggregatingEvents(Runnable block) {
        try {
            isAggregating.set(true);
            eventBuffer.set(new ArrayList<>());
            block.run();
            List<Object> events = eventBuffer.get();
            if (!events.isEmpty()) {
                delegate.multicastEvent(new PayloadApplicationEvent<>(this, new AggregatedDomainEvent(events)));
            }
        } finally {
            isAggregating.remove();
            eventBuffer.remove();
        }
    }
}
