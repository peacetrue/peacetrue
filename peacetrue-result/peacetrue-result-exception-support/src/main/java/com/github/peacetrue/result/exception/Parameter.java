package com.github.peacetrue.result.exception;

/**
 * a request parameter
 *
 * @author xiayx
 */
public class Parameter<T, V> {

    private String name;
    private T type;
    private V value;

    public Parameter() {
    }

    public Parameter(String name, T type) {
        this.name = name;
        this.type = type;
    }

    public Parameter(String name, T type, V value) {
        this(name, type);
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
