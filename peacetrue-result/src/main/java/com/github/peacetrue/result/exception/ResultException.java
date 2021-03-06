package com.github.peacetrue.result.exception;

import com.github.peacetrue.core.CodeAware;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;

import java.util.Objects;

/**
 * a exception result
 *
 * @author xiayx
 */
public class ResultException extends RuntimeException implements Result, CodeAware {

    /** the result code */
    private String code;

    public ResultException(String code, String message) {
        super(Objects.requireNonNull(message));
        this.code = Objects.requireNonNull(code);
    }

    public ResultException(Result result) {
        this(result.getCode(), result.getMessage());
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return ResultUtils.toString(this);
    }
}
