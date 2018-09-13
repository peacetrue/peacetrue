package com.github.peacetrue.sign;

/**
 * 含有一个 应用密钥
 *
 * @author xiayx
 */
public interface AppSecretCapable extends AppIdCapable {
    String getAppSecret();
}
