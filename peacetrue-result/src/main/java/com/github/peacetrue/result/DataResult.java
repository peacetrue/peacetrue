package com.github.peacetrue.result;

/**
 * hold a generic data
 *
 * @param <T> the type of {@link #getData()}
 * @author xiayx
 */
public interface DataResult<T> extends Result {

    /** the property name of {@link #getData()} */
    String DATA_PROPERTY = "data";

    /**
     * get result data
     *
     * @return a generic type data used for program
     */
    T getData();
}
