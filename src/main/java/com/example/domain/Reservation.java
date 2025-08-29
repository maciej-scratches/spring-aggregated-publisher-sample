package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.UUID;

@Entity
public class Reservation extends AbstractAggregateRoot<Reservation> {

    @Id
    private UUID id;
    private Long amount;
    @Version
    private Long version;

    public Reservation() {

    }

    public Reservation(final Long amount) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.registerEvent(new ReservationCreated(this.id));
    }
}
