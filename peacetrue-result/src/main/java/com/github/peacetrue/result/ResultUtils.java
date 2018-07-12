package com.github.peacetrue.result;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * a util class for result
 *
 * @author xiayx
 */
public abstract class ResultUtils {

    /**
     * convert the result to string
     *
     * @param result the result
     * @return the string of result
     */
    public static String toString(Result result) {
        return result.getCode() + ":" + result.getMessage();
    }

    /**
     * convert the result to string
     *
     * @param result the result
     * @return the string of result
     */
    public static String toString(DataResult<?> result) {
        return toString((Result) result) + "[" + toString(result.getData()) + "]";
    }

    private static String toString(Object object) {
        if (object == null) return "null";
        return object.getClass().isArray() ? Arrays.toString((Object[]) object) : object.toString();
    }

    /**
     * build result
     *
     * @param code    the code
     * @param message the message
     * @param data    the data
     * @return a result
     */
    public static Result build(String code, String message, @Nullable Object data) {
        return data == null ? new ResultImpl(code, message) : new DataResultImpl<>(code, message, data);
    }

    /**
     * wrap result
     *
     * @param result the result
     * @return the wrapped result
     */
    public static Result wrap(Result result) {
        return result instanceof DataResult ? new DataResultImpl<>((DataResult<?>) result) : new ResultImpl(result);
    }

}
