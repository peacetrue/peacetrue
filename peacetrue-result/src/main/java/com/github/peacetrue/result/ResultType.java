package com.github.peacetrue.result;

/**
 * the type of result
 *
 * @author xiayx
 * @see FailureType
 */
public enum ResultType {
    /** 成功时 */
    success,
    /** 失败时 */
    failure;

    /**
     * get group name include multiple result
     *
     * @return the group name
     */
    public String group() {
        return this.name() + "s";
    }
}
