package com.github.peacetrue.sign;

/**
 * @author xiayx
 */
public class SignProperties {

    public static final String PREFIX = "peacetrue.sign";

    /** 应用标识参数名 */
    private String appIdParamName = "_appId";
    /** 应用秘钥参数名 */
    private String appSecretParamName = "_appSecret";
    /** 时间搓参数名 */
    private String timestampParamName = "_timestamp";
    /** 随机串参数名 */
    private String nonceParamName = "_nonce";
    /** 签名参数名 */
    private String signParamName = "_sign";


    public String getAppIdParamName() {
        return appIdParamName;
    }

    public void setAppIdParamName(String appIdParamName) {
        this.appIdParamName = appIdParamName;
    }

    public String getAppSecretParamName() {
        return appSecretParamName;
    }

    public void setAppSecretParamName(String appSecretParamName) {
        this.appSecretParamName = appSecretParamName;
    }

    public String getTimestampParamName() {
        return timestampParamName;
    }

    public void setTimestampParamName(String timestampParamName) {
        this.timestampParamName = timestampParamName;
    }

    public String getNonceParamName() {
        return nonceParamName;
    }

    public void setNonceParamName(String nonceParamName) {
        this.nonceParamName = nonceParamName;
    }

    public String getSignParamName() {
        return signParamName;
    }

    public void setSignParamName(String signParamName) {
        this.signParamName = signParamName;
    }
}
