package com.github.peacetrue.result;

import java.util.Objects;

/**
 * a bean implement of {@link DataResult}
 *
 * @author xiayx
 */
public class DataResultImpl<T> extends ResultImpl implements DataResult<T> {

    /** the generic type data */
    private T data;

    /** used for json deserialize or spring auto bind */
    public DataResultImpl() { }

    public DataResultImpl(String code, String message, T data) {
        super(code, message);
        this.data = Objects.requireNonNull(data);
    }

    public DataResultImpl(Result result, T data) {
        super(result);
        this.data = Objects.requireNonNull(data);
    }

    public DataResultImpl(DataResult<T> result) {
        this(result, result.getData());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = Objects.requireNonNull(data);
    }

    @Override
    public String toString() {
        return ResultUtils.toString(this);
    }
}
