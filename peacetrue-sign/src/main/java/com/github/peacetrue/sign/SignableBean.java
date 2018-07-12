package com.github.peacetrue.sign;

/**
 * a signable bean
 *
 * @author xiayx
 */
public class SignableBean<T> extends AppBean implements Signable<T> {

    private T data;

    public SignableBean() {
    }

    public SignableBean(T data) {
        this.data = data;
    }

    public SignableBean(T data, String appId) {
        this.data = data;
        setAppId(appId);
    }

    public SignableBean(T data, String appId, String appSecret) {
        this.data = data;
        setAppId(appId);
        setAppSecret(appSecret);
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
