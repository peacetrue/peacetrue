package com.github.peacetrue.result;

/**
 * the type of result
 *
 * @author xiayx
 * @see FailureType
 */
public enum ResultType {

    success,
    failure;

    /**
     * get group name include multiple failure
     *
     * @return the group name
     */
    public String group() {
        return this.name() + "s";
    }
}
