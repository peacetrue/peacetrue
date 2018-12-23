package com.github.peacetrue.sign;

/**
 * 可被签名的数据
 *
 * @author xiayx
 */
public class SignableData<T> extends AppBean implements Signable<T> {

    private T data;

    public SignableData() {
    }

    public SignableData(T data) {
        this.data = data;
    }

    public SignableData(T data, String appId, String appSecret) {
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
