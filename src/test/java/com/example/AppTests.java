package com.example;

import com.example.application.AppLevelEvent;
import com.example.application.CreateReservationUseCase;
import com.example.domain.ReservationCreated;
import com.example.publisher.AggregatingEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@SpringBootTest
class AppTests {
    @Autowired
    private AggregatingEventPublisher publisher;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CreateReservationUseCase createReservationUseCase;

    @Test
    void contextLoads() {
        publisher.publishEvent(new ReservationCreated(UUID.randomUUID()));

        transactionTemplate.executeWithoutResult(status -> {
            publisher.withAggregatingEvents(() -> {
                createReservationUseCase.execute(10L);

                publisher.publishEvent(new AppLevelEvent("App level event inside transaction"));

                createReservationUseCase.execute(20L);
            });
        });
    }

}
