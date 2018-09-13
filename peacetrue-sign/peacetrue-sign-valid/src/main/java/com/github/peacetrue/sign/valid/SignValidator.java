package com.github.peacetrue.sign.valid;

/**
 * 签名验证器
 *
 * @author xiayx
 */
public interface SignValidator<T> {

    /**
     * valid the signed value
     *
     * @param signedValue the signed value
     * @param secret      the secret
     * @return 签名是否有效
     */
    boolean valid(T signedValue, String secret);

}
