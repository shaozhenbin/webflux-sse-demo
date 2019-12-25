package com.szb.webfluxssedemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Random;

@Component
@Slf4j
public class ProfileCreatedEventProcessor {

    private final Flux<ProfileCreatedEvent> events;
    private final ObjectMapper objectMapper;

    public ProfileCreatedEventProcessor(ProfileCreatedEventPublisher publisher,
                                        ObjectMapper objectMapper) {
        this.events = Flux.create(publisher).share();
        this.objectMapper = objectMapper;
    }

    public Flux<String> profile() {
        log.debug("--------------profile--------------");
        return Flux.from(events).map(pce -> {
            try {
                return objectMapper.writeValueAsString(pce) + "\n\n";
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Flux<String> profile2() {
        log.debug("--------------profile2--------------");
        return this.events.map(pce -> {
                return new Random() + "\n\n";
        });
    }

//   @EventListener(condition = "#event.success")
    // @Async
    //使用注解方式,spring events默认同步处理，若开启异步，需配置线程池，每次开启一个新线程处理，线程创建和销毁开销大
    @EventListener
    public void handleSuccessful(ProfileCreatedEvent event) {
        log.debug("--------------handleSuccessful--------------");
        System.out.println("Handling generic event (conditional).");
    }

    @EventListener
    public void handleSuccessful2(ProfileCreatedEvent event) {
        log.debug("--------------handleSuccessful2--------------");
        System.out.println("Handling generic event (conditional2).");
    }

}
