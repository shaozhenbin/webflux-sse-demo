package com.szb.webfluxssedemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.core.convert.MappingConfiguration;
import org.springframework.data.redis.core.index.IndexConfiguration;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Profile> reactiveRedisTemplate(
            final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializer<Profile> valueSerializer = new Jackson2JsonRedisSerializer(Profile.class);
        RedisSerializationContext<String, Profile> serializationContext = RedisSerializationContext
                .<String, Profile>newSerializationContext()
                .key(keySerializer)
                .value(valueSerializer)
                .hashKey(keySerializer)
                .hashValue(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<String, Profile>(reactiveRedisConnectionFactory, serializationContext);
    }

    //reactive redis object序列化报错
//    @Bean
//    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
//            final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
//        RedisSerializer<String> keySerializer = new StringRedisSerializer();
//        RedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
//                .<String, Object>newSerializationContext()
//                .key(keySerializer)
//                .value(valueSerializer)
//                .hashKey(keySerializer)
//                .hashValue(valueSerializer)
//                .build();
//        return new ReactiveRedisTemplate<String, Object>(reactiveRedisConnectionFactory, serializationContext);
//    }


}
