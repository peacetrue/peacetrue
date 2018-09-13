package com.github.peacetrue.sign.append;

import com.github.peacetrue.sign.AppSecretCapable;

import javax.annotation.Nullable;

/**
 * used to encode data
 *
 * @author xiayx
 */
public interface SignService<T, R> {

    /**
     * sign the data
     *
     * @param data      the data
     * @param appSecret the appSecret
     * @return the signed value
     */
    R sign(T data, @Nullable AppSecretCapable appSecret);

    /**
     * sign the data
     *
     * @param data the data
     * @return the signed value
     */
    default R sign(T data) {
        return sign(data, null);
    }

}
