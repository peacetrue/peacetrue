package com.github.peacetrue.sign;

/**
 * 可被签名的数据
 *
 * @author xiayx
 */
public class SignBean<T> extends AppBean implements SignCapable<T> {

    private T data;

    public SignBean() {
    }

    public SignBean(T data) {
        this.data = data;
    }

    public SignBean(T data, String appId, String appSecret) {
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
