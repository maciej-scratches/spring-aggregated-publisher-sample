package com.example;

import com.example.publisher.AggregatingApplicationEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AggregatedPublisherDemoApplicationTests {
    @Autowired
    private AggregatingApplicationEventPublisher publisher;

    @Test
    void contextLoads() {
        publisher.publishEvent(new MyDomainEvent("foo"));

        publisher.withAggregatingEvents(() -> {
            publisher.publishEvent(new MyDomainEvent("bar"));
            publisher.publishEvent(new MyDomainEvent("baz"));
        });
    }

}
