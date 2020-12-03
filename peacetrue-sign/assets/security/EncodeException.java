package com.github.peacetrue.spring.security;

/**
 * 编码异常
 *
 * @author xiayx
 */
public class EncodeException extends RuntimeException {

    /** 欲编码字符串 */
    private String string;
    /** 编码类型 */
    private String type;

    private static String format(String string, String type) {
        return String.format("cannot use '%s' to encode '%s'", type, string);
    }

    public EncodeException(String string, String type) {
        super(format(string, type));
    }

    public EncodeException(String string, String type, Throwable cause) {
        super(format(string, type), cause);
    }

    public String getString() {
        return string;
    }

    public String getType() {
        return type;
    }
}
