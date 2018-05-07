package com.github.peacetrue.result;

import java.util.Objects;

/**
 * a bean implement of {@link Result}
 *
 * @author xiayx
 */
public class ResultImpl implements Result {

    private String code;
    private String message;

    public ResultImpl(String code, String message) {
        this.code = Objects.requireNonNull(code);
        this.message = Objects.requireNonNull(message);
    }

    public ResultImpl(Result result) {
        this(result.getCode(), result.getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
