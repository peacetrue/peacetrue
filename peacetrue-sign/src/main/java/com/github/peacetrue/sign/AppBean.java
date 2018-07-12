package com.github.peacetrue.sign;

/**
 * the bean of app
 *
 * @author xiayx
 */
public class AppBean implements AppSecret {

    private String appId;
    private String appSecret;

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
