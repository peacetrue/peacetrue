package com.github.peacetrue.result.dubbo;

import com.github.peacetrue.result.exception.ResultException;

/**
 * @author xiayx
 */
public interface ResultDubboService {
    default void throwException() {
        throw new ResultException("dubboException", "");
    }
}
