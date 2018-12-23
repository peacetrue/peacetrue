package com.github.peacetrue.sign.validator;

/**
 * 无效的签名异常
 *
 * @author xiayx
 */
public class InvalidSignException extends InvalidAppIdException {

    private String sign;

    public InvalidSignException(String appId, String sign) {
        super(appId, "the sing '" + sign + "' for app '" + appId + "' is invalid");
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
