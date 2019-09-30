package com.github.peacetrue.uuid;

/**
 * 唯一标识溢出异常，
 * 唯一标识如果有范围限制，
 * 超出范围会导致此异常
 *
 * @author xiayx
 */
public class UUIDOverflowException extends RuntimeException {

    /** 溢出值 */
    private Object value;

    public UUIDOverflowException() {
    }

    public UUIDOverflowException(Object value) {
        super(String.format("the value '%s' overflow the max limit", value));
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
