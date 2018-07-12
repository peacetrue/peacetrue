package com.github.peacetrue.sign;

/**
 * the bean with a appSecret
 *
 * @author xiayx
 */
public interface AppSecret extends AppId {
    /**
     * get secret
     *
     * @return the secret
     */
    String getAppSecret();
}
