package com.example.publisher;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
public class AggregatingEventPublisherConfiguration {

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ConfigurableBeanFactory beanFactory) {
        SimpleApplicationEventMulticaster delegate = new SimpleApplicationEventMulticaster(beanFactory);
        return new AggregatingMulticaster(delegate);
    }
}
