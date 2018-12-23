package com.github.peacetrue.sign.validator;

import com.github.peacetrue.sign.AppIdCapable;

/**
 * used when a identifier is invalid
 *
 * @author xiayx
 */
public class InvalidAppIdException extends RuntimeException implements AppIdCapable {

    private String appId;

    public InvalidAppIdException(String appId) {
        this(appId, "the appId '" + appId + "' is invalid");
    }

    public InvalidAppIdException(String appId, String message) {
        super(message);
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}
