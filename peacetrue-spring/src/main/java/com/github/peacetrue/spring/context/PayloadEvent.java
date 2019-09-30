package com.github.peacetrue.spring.context;

import org.springframework.context.ApplicationEvent;

/** 自定义的负载事件用于取代{@link org.springframework.context.PayloadApplicationEvent} */
public class PayloadEvent<T> extends ApplicationEvent {

    private T payload;

    public PayloadEvent(String eventName, T payload) {
        super(eventName);
        this.payload = payload;
    }

    public String getEventName() {
        return (String) getSource();
    }

    public T getPayload() {
        return payload;
    }
}
