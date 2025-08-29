# Spring aggregating `ApplicationEventPublisher` demo

By default, all events published through Spring's `ApplicationEventPublisher` are handled one by one. 
However, in some scenarios, it might be beneficial to aggregate events and process them in batches. 

For example, there are multiple events published within a single transaction that trigger sending an email to a user.
Instead of sending multiple emails, we want to send a single email with aggregated information.

This is where event aggregation comes into play.

```java

@Service
class MyService {
    private final AggregatingEventPublisher publisher;
    
    // constructor omitted for brevity
    
    @Transactional
    void doSomething() {
        // some business logic
        publisher.publishEvent(new MyEvent(...));
        // some more business logic
        publisher.publishEvent(new MyEvent(...));
    }
}

@Component
class MyEventListener {
    @EventListener
    void handleMyEvents(AggregatedDomainEvent events) {
        LOGGER.info("Received aggregated event with events:", event.getEvents());
    }
}
```

The implementation provided in this sample projects covers both publishing events through `ApplicationEventPublisher` and events first registered in Spring Data's aggregates (like ones extending from `AbstractAggregateRoot`). 