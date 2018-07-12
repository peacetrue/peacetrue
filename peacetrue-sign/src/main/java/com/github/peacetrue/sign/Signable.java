package com.github.peacetrue.sign;

/**
 * a signable data
 *
 * @author xiayx
 */
public interface Signable<T> extends AppSecret {

    /**
     * get the data
     *
     * @return the data
     */
    T getData();
}
