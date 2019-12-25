package com.szb.webfluxssedemo;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileRepository {

    Mono<Profile> save(Profile profile);

    Mono<Profile> findById(String id);

    Flux<Profile> findAll();

    Mono<Long> count();

    Mono<Long> deleteById(String id);

    Flux<Profile> saveAll(Flux<Profile> just);
}
