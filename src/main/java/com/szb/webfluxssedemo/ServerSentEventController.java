package com.szb.webfluxssedemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Random;

@RestController
@Slf4j
public class ServerSentEventController {

    private ProfileCreatedEventProcessor processor;

    public ServerSentEventController(ProfileCreatedEventProcessor processor) {
        this.processor = processor;
    }

    @GetMapping(path = "/sse/profiles", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> profiles() {
        log.debug("--------------profile--------------");
        return processor.profile();
    }

    @GetMapping(path = "/sse/profiles2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> profiles2() {
        log.debug("--------------profile2--------------");
        return processor.profile2();
    }
}
