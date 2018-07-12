package com.github.peacetrue.result;

import com.github.peacetrue.core.CodeAware;

import java.io.Serializable;
import java.util.Objects;

/**
 * a bean implement of {@link Result}
 *
 * @author xiayx
 */
public class ResultImpl implements Result, CodeAware, Serializable {

    private String code;
    private String message;

    /** used for json deserialize or spring auto bind */
    public ResultImpl() { }

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

    @Override
    public void setCode(String code) {
        this.code = Objects.requireNonNull(code);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ResultUtils.toString(this);
    }
}
