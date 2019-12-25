package com.szb.webfluxssedemo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@Data
@EqualsAndHashCode(callSuper = false)
public class GenericEvent<T> extends ApplicationEvent {

    public GenericEvent(T source) {
        super(source);
    }
}
