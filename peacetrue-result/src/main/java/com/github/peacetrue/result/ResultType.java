package com.github.peacetrue.result;

/**
 * the type of result
 *
 * @author xiayx
 * @see ErrorType
 */
public enum ResultType {

    success,
    error;

    /**
     * get group name include multiple error
     *
     * @return the group name
     */
    public String group() {
        return this.name() + "s";
    }
}
