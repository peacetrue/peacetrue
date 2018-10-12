package com.github.peacetrue.dubbo;

/**
 * @author xiayx
 */
public interface SomeService {
    default String getConsumerApplicationName() {
        return DubboContextFilter.getConsumerApplicationName();
    }
}
