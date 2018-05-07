package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.Result;

/**
 * a data result exception
 *
 * @author xiayx
 */
public class DataResultException extends ResultException implements DataResult<Object> {

    /** the generic type data */
    private Object data;

    public DataResultException(String code, String message) {
        super(code, message);
    }

    public DataResultException(String code, String message, Object data) {
        super(code, message);
        this.data = data;
    }

    public DataResultException(Result result) {
        super(result);
    }

    public DataResultException(Result result, Object data) {
        super(result);
        this.data = data;
    }

    public DataResultException(DataResult<?> result) {
        this(result, result.getData());
    }

    @Override
    public Object getData() {
        return data;
    }
}
