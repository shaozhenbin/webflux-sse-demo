package com.szb.webfluxssedemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
class GenericPublisher<T extends ApplicationEvent> implements
        ApplicationListener<T>, // <1>
    Consumer<FluxSink<T>> { //<2>

    private final Executor executor;
    private final BlockingQueue<T> queue =
        new LinkedBlockingQueue<T>(); // <3>

    GenericPublisher(@Qualifier("applicationTaskExecutor") Executor executor) {
        this.executor = executor;
    }

    // <4>
    @Override
    public void onApplicationEvent(T event) {
        this.queue.offer(event);
    }

     @Override
    public void accept(FluxSink<T> sink) {

        this.executor.execute(() -> {
            while (true)
                try {
                    T event = queue.take(); // <5>
                    sink.next(event); // <6>
                }
                catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }
}
