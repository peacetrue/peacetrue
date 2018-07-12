package com.github.peacetrue.result;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * a Result builder
 *
 * @author xiayx
 */
public interface ResultBuilder {

    /**
     * build {@link Result} from code
     *
     * @param code the custom result code
     * @return the {@link DataResult}
     */
    default Result build(String code) {
        return build(code, (Object[]) null);
    }

    /**
     * build {@link Result} from code and arguments
     *
     * @param code      the custom result code
     * @param arguments the arguments
     * @return the {@link DataResult}
     */
    default Result build(String code, @Nullable Object[] arguments) {
        return build(code, arguments, null);
    }

    /**
     * build {@link Result} from code and locale
     *
     * @param code   the custom result code
     * @param locale the locale
     * @return the {@link Result}
     */
    default Result build(String code, @Nullable Locale locale) {
        return build(code, null, locale);
    }


    /**
     * build {@link Result} from code, arguments, data and locale
     *
     * @param code      the custom result code
     * @param arguments the arguments
     * @param locale    the locale
     * @return the {@link DataResult}
     */
    Result build(String code, @Nullable Object[] arguments, @Nullable Locale locale);


    /**
     * build {@link Result} from code, arguments and data
     *
     * @param code the custom result code
     * @param data the data
     * @param <T>  the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> build(String code, T data) {
        return build(code, null, data);
    }


    /**
     * build {@link Result} from code, arguments and data
     *
     * @param code   the custom result code
     * @param data   the data
     * @param locale the locale
     * @param <T>    the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> build(String code, T data, @Nullable Locale locale) {
        return build(code, null, data, locale);
    }


    /**
     * build {@link Result} from code, arguments and data
     *
     * @param code      the custom result code
     * @param arguments the arguments
     * @param data      the data
     * @param <T>       the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> build(String code, @Nullable Object[] arguments, T data) {
        return build(code, arguments, data, null);
    }

    /**
     * build {@link DataResult} from code, arguments, data and locale
     *
     * @param code      the custom result code
     * @param arguments the arguments
     * @param data      the data
     * @param locale    the locale
     * @param <T>       the type of data
     * @return the {@link DataResult}
     */
    <T> DataResult<T> build(String code, @Nullable Object[] arguments, T data, @Nullable Locale locale);


    /**
     * build success {@link Result}
     *
     * @return the {@link Result}
     */
    default Result success() {
        return success(null);
    }


    /**
     * build success {@link Result}
     *
     * @param locale the locale
     * @return the {@link Result}
     */
    Result success(@Nullable Locale locale);

    /**
     * build success {@link Result} from data
     *
     * @param data the data
     * @param <T>  the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> success(T data) {
        return success(data, null);
    }


    /**
     * build success {@link Result} from data
     *
     * @param data   the data
     * @param locale the locale
     * @param <T>    the type of data
     * @return the {@link DataResult}
     */
    <T> DataResult<T> success(T data, @Nullable Locale locale);


    /**
     * build failure {@link Result}
     *
     * @return the {@link Result}
     */
    default Result failure() {
        return failure(null);
    }


    /**
     * build failure {@link Result}
     *
     * @param locale the locale
     * @return the {@link Result}
     */
    Result failure(@Nullable Locale locale);


    /**
     * build failure {@link Result} from data
     *
     * @param data the data
     * @param <T>  the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> failure(T data) {
        return failure(data, null);
    }

    /**
     * build failure {@link Result} from data
     *
     * @param data   the data
     * @param locale the locale
     * @param <T>    the type of data
     * @return the {@link DataResult}
     */
    <T> DataResult<T> failure(T data, @Nullable Locale locale);

}
