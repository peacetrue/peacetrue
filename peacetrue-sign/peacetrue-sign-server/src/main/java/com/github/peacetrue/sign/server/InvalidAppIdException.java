package com.github.peacetrue.sign.server;

import com.github.peacetrue.sign.AppId;

/**
 * used when a identifier is invalid
 *
 * @author xiayx
 */
public class InvalidAppIdException extends RuntimeException implements AppId {

    private String appId;

    public InvalidAppIdException(String appId) {
        super("the appId '" + appId + "' is invalid");
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}
