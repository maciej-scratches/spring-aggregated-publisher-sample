package com.example;

public class MyDomainEvent {
    private final String name;

    public MyDomainEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

