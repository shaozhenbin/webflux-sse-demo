package com.szb.webfluxssedemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.time.Duration;

@Data // <2>
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
     // <3>
    private String id;

    // <4>
    private String email;


}
