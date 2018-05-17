package com.github.peacetrue.result;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * a builder of Result
 *
 * @author xiayx
 */
public interface ResultBuilder {

    /**
     * build result from code and arguments
     *
     * @param code      this custom result code
     * @param arguments the arguments
     * @return the {@link DataResult}
     */
    default Result build(String code, @Nullable Object[] arguments) {
        return build(code, arguments, null);
    }

    /**
     * build {@link DataResult} from code and locale
     *
     * @param code   this custom result code
     * @param locale the locale
     * @return the {@link Result}
     */
    default Result build(String code, @Nullable Locale locale) {
        return build(code, null, locale);
    }


    /**
     * build result from code, arguments and data
     *
     * @param code      this custom result code
     * @param arguments the arguments
     * @param data      the data
     * @param <T>       the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> build(String code, @Nullable Object[] arguments, @Nullable T data) {
        return build(code, arguments, data, null);
    }

    /**
     * build {@link DataResult} from code, arguments, data and locale
     *
     * @param code      this custom result code
     * @param arguments the arguments
     * @param locale    the locale
     * @param <T>       the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> build(String code, @Nullable Object[] arguments, @Nullable Locale locale) {
        return build(code, arguments, null, locale);
    }

    /**
     * build {@link DataResult} from code, arguments, data and locale
     *
     * @param code      this custom result code
     * @param arguments the arguments
     * @param data      the data
     * @param locale    the locale
     * @param <T>       the type of data
     * @return the {@link DataResult}
     */
    <T> DataResult<T> build(String code, @Nullable Object[] arguments, @Nullable T data, @Nullable Locale locale);


    /**
     * build success result
     *
     * @return the {@link Result}
     */
    Result build();


    /**
     * build success result with data
     *
     * @param data the data
     * @param <T>  the type of data
     * @return the {@link DataResult}
     */
    <T> DataResult<T> build(T data);

}
