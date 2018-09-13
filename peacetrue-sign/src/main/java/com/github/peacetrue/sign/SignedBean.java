package com.github.peacetrue.sign;

import com.github.peacetrue.core.DataCapable;

/**
 * 已签名的数据
 *
 * @param <T> the type of data
 * @author xiayx
 */
public class SignedBean<T> implements AppIdCapable, DataCapable<T> {

    private String appId;
    private String nonce;
    private Long timestamp;
    private String sign;
    private T data;

    @Override
    public String toString() {
        return "Signed{" +
                "appId='" + appId + '\'' +
                ", nonce='" + nonce + '\'' +
                ", timestamp=" + timestamp +
                ", sign='" + sign + '\'' +
                ", data=" + data +
                '}';
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
