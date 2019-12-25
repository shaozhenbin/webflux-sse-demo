package com.szb.webfluxssedemo;

import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    private ReactiveHashOperations<String, String, Profile> reactiveHashOperations;

    public static final String PROFILE_HASH_KEY = "sse:profile";

    public ProfileRepositoryImpl(ReactiveRedisTemplate<String, Object> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.reactiveHashOperations = reactiveRedisTemplate.opsForHash();
    }

    @Override
    public Mono<Profile> save(Profile profile) {
        return reactiveHashOperations.put(PROFILE_HASH_KEY, profile.getId(), profile)
                .then(findById(profile.getId()));
    }

    @Override
    public Mono<Profile> findById(String id) {
        return reactiveHashOperations.get(PROFILE_HASH_KEY, id).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<Profile> findAll() {
        return reactiveHashOperations.values(PROFILE_HASH_KEY);
    }

    @Override
    public Mono<Long> count() {
        return reactiveHashOperations.size(PROFILE_HASH_KEY);
    }

    @Override
    public Mono<Long> deleteById(String id) {
        return reactiveHashOperations.remove(PROFILE_HASH_KEY, id);
    }

    @Override
    public Flux<Profile> saveAll(Flux<Profile> just) {
        return reactiveHashOperations.putAll(PROFILE_HASH_KEY,
                just.collectMap(x -> x.getId(), x -> x).block()).thenMany(findAll());
    }
}
