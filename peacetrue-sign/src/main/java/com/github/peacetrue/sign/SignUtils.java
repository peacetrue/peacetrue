package com.github.peacetrue.sign;

import com.github.peacetrue.spring.security.MessageDigestUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * a util class for sign
 *
 * @author xiayx
 */
public abstract class SignUtils {

    /** 应用标识参数名称 */
    public static String appId = "appId";
    /** 签名参数名称 */
    public static String sign = "sign";

    /** 生成签名 */
    public static String generateSign(Map<String, String> params, String secret) {
        return generateSign(concat(sort(params)), secret);
    }

    /** 生成签名 */
    public static String generateSign(String params, String secret) {
        return MessageDigestUtils.encode(MessageDigestUtils.getMD5(), params + secret);
    }

    /** 字典序排列 */
    public static <T> Map<String, T> sort(Map<String, T> params) {
        return params.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), LinkedHashMap::new));
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

    /** 串联参数 */
    public static String concat(Map<String, String> params) {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + Objects.toString(entry.getValue(), ""))
                .collect(Collectors.joining(""));
    }

    /** 签名参数 */
    public static void sign(Map<String, String> params, String appId, String appSecret) {
        params.put(SignUtils.appId, appId);
        Map<String, String> sorted = sort(new HashMap<>(params));
        params.put(SignUtils.sign, generateSign(sorted, appSecret));
    }

    public static Map<String, String> toString(Map<String, ?> params) {
        return params.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, SignUtils::toString));
    }

    private static String toString(Map.Entry<String, ?> entry) {
        return toString(entry.getValue());
    }

    @SuppressWarnings("unchecked")
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
