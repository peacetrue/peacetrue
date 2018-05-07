package com.github.peacetrue.result;

/**
 * a bean implement of {@link DataResult}
 *
 * @author xiayx
 */
public class DataResultImpl<T> extends ResultImpl implements DataResult<T> {

    /** the generic type data */
    private T data;

    public DataResultImpl(String code, String message) {
        super(code, message);
    }

    public DataResultImpl(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public DataResultImpl(Result result) {
        super(result);
    }

    public DataResultImpl(Result result, T data) {
        super(result);
        this.data = data;
    }

    public DataResultImpl(DataResult<T> result) {
        this(result, result.getData());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("%s(%s) with data %s", getMessage(), getCode(), getData());
    }
}
