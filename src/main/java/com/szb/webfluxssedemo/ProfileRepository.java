package com.szb.webfluxssedemo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}
