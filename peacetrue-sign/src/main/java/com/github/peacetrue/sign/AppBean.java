package com.github.peacetrue.sign;

import java.io.Serializable;

/**
 * 应用Bean
 *
 * @author xiayx
 */
public class AppBean implements AppSecretCapable, Serializable {

    private String appId;
    private String appSecret;

    public AppBean() {
    }

    public AppBean(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
