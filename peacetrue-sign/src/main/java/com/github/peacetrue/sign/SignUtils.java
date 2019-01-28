package com.github.peacetrue.sign;

import com.github.peacetrue.security.MessageDigestUtils;

import java.util.*;
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
    public static String generateSign(Map<String, ?> params, String secret) {
        String string = concat(params);
        return generateSign(string, secret);
    }

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

    /** 串联参数 */
    public static String concat(Map<String, ?> params) {
        return new ArrayList<>(params.entrySet()).stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(SignUtils::toString)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("&"));
    }

    private static String toString(Map.Entry<String, ?> entry) {
        String value = toString(entry.getValue());
        if (value == null) return null;
        return Objects.requireNonNull(entry.getKey()) + "=" + value;
    }

    private static String toString(Object value) {
        if (value == null) return null;

        if (value instanceof Object[]) {
            Object[] values = (Object[]) value;
            if (values.length == 0) return null;
            if (values.length == 1) return values[0].toString();
            return Arrays.stream(values).map(Object::toString).collect(Collectors.joining(""));
        } else if (value instanceof Collection) {
            Collection values = (Collection) value;
            if (values.isEmpty()) return null;
            return (String) values.stream().map(Object::toString).collect(Collectors.joining(""));
        } else {
            return value.toString();
        }
    }

}
