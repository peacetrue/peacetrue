package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * the service for exception convert
 *
 * @author xiayx
 */
public interface ExceptionConvertService {

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
