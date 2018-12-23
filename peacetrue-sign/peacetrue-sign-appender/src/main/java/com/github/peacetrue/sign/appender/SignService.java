package com.github.peacetrue.sign.appender;

import com.github.peacetrue.sign.AppSecretCapable;

import javax.annotation.Nullable;

/**
 * used to encode data
 *
 * @param <T> 想要签名的数据
 * @param <R> 签名后的数据
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
