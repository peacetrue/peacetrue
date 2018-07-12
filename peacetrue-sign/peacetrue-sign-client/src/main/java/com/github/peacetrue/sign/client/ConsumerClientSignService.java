package com.github.peacetrue.sign.client;

/**
 * sign data with json
 *
 * @author xiayx
 */
public class ConsumerClientSignService extends AbstractClientSignService {

    private String appId;
    private String appSecret;

    @Override
    public Object sign(Object data, String appId, String appSecret) {
        if (appId == null || appSecret == null) {
            appId = getAppId();
            appSecret = getAppSecret();
        }
        return super.sign(data, appId, appSecret);
    }

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
