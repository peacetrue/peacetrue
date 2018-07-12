package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * used to convert {@link Exception} to {@link Result}
 *
 * @author xiayx
 * @see ConditionalExceptionConverter
 * @see ExceptionConvertService
 */
public interface ExceptionConverter<T extends Exception> {

    /**
     * convert {@link Exception} to {@link Result}
     *
     * @param exception the exception need to converted
     * @param locale    the locale
     * @return the result converted by {@code exception}
     */
    Result convert(T exception, @Nullable Locale locale);

    /**
     * convert {@link Exception} to {@link Result}
     *
     * @param exception the exception need to converted
     * @return the result converted by {@code exception}
     */
    default Result convert(T exception) {
        return convert(exception, null);
    }


}
