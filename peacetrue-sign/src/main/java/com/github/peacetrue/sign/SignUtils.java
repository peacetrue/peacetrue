package com.github.peacetrue.sign;

import com.github.peacetrue.security.MessageDigestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * a util class for sign
 *
 * @author xiayx
 */
public abstract class SignUtils {

    /**
     * generate sign
     *
     * @param params the params
     * @param secret the secret
     * @return the generated sign
     */
    public static String generateSign(String params, String secret) {
        return MessageDigestUtils.encode(MessageDigestUtils.getMD5(), params + secret);
    }

    /**
     * generate sign
     *
     * @param params the params
     * @param secret the secret
     * @return the generated sign
     */
    public static String generateSign(Map<String, ?> params, String secret) {
        String string = new ArrayList<>(params.entrySet()).stream()
                .filter(entry -> entry.getValue() != null)
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(entry -> entry.getKey() + entry.getValue().toString())
                .collect(Collectors.joining(""));
        return generateSign(string, secret);
    }

    /**
     * wrap the request data to a signable data
     *
     * @param data the request data
     * @return the signed
     */
    public static Object wrap(Object data) {
        return wrap(data, null);
    }

    /**
     * wrap the request data to a signable data
     *
     * @param data the request data
     * @return the signed
     */

    public static Object wrap(Object data, String id) {
        return wrap(data, id, null);
    }

    /**
     * wrap the request data to a signable data
     *
     * @param data      the request data
     * @param appId     the appId
     * @param appSecret the appSecret
     * @return the signed
     */
    public static Object wrap(Object data, String appId, String appSecret) {
        SignableBean<Object> bean = new SignableBean<>();
        bean.setAppId(appId);
        if (bean.getAppId() == null && data instanceof AppId) {
            bean.setAppId(((AppId) data).getAppId());
        }
        bean.setAppSecret(appSecret);
        bean.setData(data);
        return bean;
    }


}
