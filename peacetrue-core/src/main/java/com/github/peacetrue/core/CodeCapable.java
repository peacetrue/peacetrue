package com.github.peacetrue.core;

/**
 * the object which has a code property.
 * the code property represents the unique developer identification code
 *
 * @author xiayx
 * @see CodeAware
 */
public interface CodeCapable {

    /**
     * get the unique developer identification code
     *
     * @return the code
     */
    String getCode();
}
