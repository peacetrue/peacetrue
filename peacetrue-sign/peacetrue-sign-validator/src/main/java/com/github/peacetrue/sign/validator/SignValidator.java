package com.github.peacetrue.sign.validator;

/**
 * 签名验证器
 *
 * @author xiayx
 */
public interface SignValidator<T> {

    /**
     * valid the signed value
     *
     * @param signedData the signed value
     * @param secret     the secret
     * @return 签名是否有效
     */
    boolean valid(T signedData, String secret);

}
