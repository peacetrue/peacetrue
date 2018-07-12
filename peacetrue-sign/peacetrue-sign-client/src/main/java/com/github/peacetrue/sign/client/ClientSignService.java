package com.github.peacetrue.sign.client;

import javax.annotation.Nullable;

/**
 * a service for client sign
 *
 * @author xiayx
 */
public interface ClientSignService {

    /**
     * sign the data
     *
     * @param data      the data
     * @param appId     the appId
     * @param appSecret the appSecret
     * @return the signed value
     */
    Object sign(Object data, @Nullable String appId, @Nullable String appSecret);

    /**
     * sign the data
     *
     * @param data  the data
     * @param appId the appId
     * @return the signed value
     */
    default Object sign(Object data, @Nullable String appId) {
        return sign(data, appId, null);
    }

    /**
     * sign the data
     *
     * @param data the data
     * @return the signed value
     */
    default Object sign(Object data) {
        return sign(data, null);
    }

}
