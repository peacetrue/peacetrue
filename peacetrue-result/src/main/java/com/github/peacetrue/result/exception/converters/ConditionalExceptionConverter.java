package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * the conditional exception converter
 *
 * @author xiayx
 * @see ExceptionConverter
 * @see com.github.peacetrue.result.exception.ExceptionConvertService
 */
public interface ConditionalExceptionConverter {

    /**
     * determine if this exception is supported
     *
     * @param exception the exception
     * @return true if supports, otherwise false
     */
    boolean supports(Exception exception);

    /**
     * convert {@link Exception} to {@link Result}
     *
     * @param exception the exception need to converted
     * @param locale    the locale
     * @return the result converted by {@code exception}
     */
    Result convert(Exception exception, @Nullable Locale locale);

    /**
     * convert {@link Exception} to {@link Result}
     *
     * @param exception the exception need to converted
     * @return the result converted by {@code exception}
     */
    default Result convert(Exception exception) {
        return convert(exception, null);
    }


}
