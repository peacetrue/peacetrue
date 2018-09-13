package com.github.peacetrue.sign.valid;

import com.github.peacetrue.sign.AppIdCapable;

/**
 * used when a identifier is invalid
 *
 * @author xiayx
 */
public class InvalidAppIdException extends RuntimeException implements AppIdCapable {

    private String appId;

    public InvalidAppIdException(String appId) {
        super("the appId '" + appId + "' is invalid");
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}
