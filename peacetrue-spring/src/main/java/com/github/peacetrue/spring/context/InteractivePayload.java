package com.github.peacetrue.spring.context;

import java.util.*;

/** 可交互的负载 */
public class InteractivePayload<S> {

    /** 原始负载 */
    private S payload;
    /** 共享变量 */
    private Map<String, Object> variables = new LinkedHashMap<>();
    /** 共享异常 */
    private List<Throwable> exceptions = new LinkedList<>();

    public InteractivePayload(S payload) {
        this.payload = payload;
    }

    public S getPayload() {
        return payload;
    }

    @SuppressWarnings("unchecked")
    public <T> T setVariable(String name, Object value) {
        return (T) variables.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getVariable(String name) {
        return (T) variables.get(name);
    }

    public void addException(Throwable throwable) {
        this.exceptions.add(throwable);
    }

    public List<Throwable> getExceptions() {
        return Collections.unmodifiableList(this.exceptions);
    }
}
