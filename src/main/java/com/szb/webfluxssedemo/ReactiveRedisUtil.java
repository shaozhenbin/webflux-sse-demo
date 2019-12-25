package com.szb.webfluxssedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
@Component
public class ReactiveRedisUtil {

    private ReactiveRedisTemplate<String, Object> redisTemplate;

    public ReactiveRedisUtil(ReactiveRedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间 (秒)
     * @return
     */
    public Mono<Boolean> expire(String key, long time) {
        try {
            if (time > 0) {
                return redisTemplate.expire(key, Duration.ofSeconds(time));
            }
            return Mono.create(sink -> sink.success(true));
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.create(sink -> sink.success(false));
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key      键
     * @param time     时间
     * @param timeUnit 时间单位
     * @return
     */
    public Mono<Boolean> expire(String key, long time, TemporalUnit timeUnit) {
        try {
            if (time > 0) {
                return redisTemplate.expire(key, Duration.of(time, timeUnit));
            }
            return Mono.create(sink -> sink.success(true));
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.create(sink -> sink.success(false));
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间（秒）返回0表示永久有效
     */
    public Mono<Duration> getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断Key是否存在
     *
     * @param key 键
     * @return true 存在 false 不存在
     */
    public Mono<Boolean> hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.create(sink -> sink.success(false));
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public Mono<Long> del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                return redisTemplate.delete(key[0]);
            } else {
                return redisTemplate.delete(key);
            }
        }
        return Mono.empty();
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Mono<Object> get(String key) {
        return key == null ? Mono.empty() : redisTemplate.opsForValue().get(key);
    }
}
