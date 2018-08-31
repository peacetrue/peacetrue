package com.github.peacetrue.core;

/**
 * the object which has a id property.
 * the id property represents the unique program identification code
 *
 * @param <T> the type of id
 * @author xiayx
 */
public interface IdCapable<T> {

    /**
     * get the unique program identification code
     *
     * @return the id
     */
    T getId();
}
