package com.szb.webfluxssedemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
class ProfileCreatedEventPublisher extends GenericPublisher<ProfileCreatedEvent> {

    ProfileCreatedEventPublisher(@Qualifier("applicationTaskExecutor") Executor executor) {
        super(executor);
    }
}
