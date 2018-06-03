package com.github.peacetrue.core;

/**
 * the object which has a data property.
 * the data property represents the actual effective data.
 *
 * @param <T> the type of data
 * @author xiayx
 */
public interface Data<T> {

    /**
     * get the unique program identification code
     *
     * @return the id
     */
    T getData();
}
