package com.github.peacetrue.core;

/**
 * can set a id
 *
 * @param <T> the type of id
 * @author xiayx
 * @see IdCapable
 */
public interface IdAware<T> {
    void setId(T id);
}
