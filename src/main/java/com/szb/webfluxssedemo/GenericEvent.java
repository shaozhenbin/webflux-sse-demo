package com.szb.webfluxssedemo;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class GenericEvent<T> extends ApplicationEvent {

    public GenericEvent(T source) {
        super(source);
    }
}
