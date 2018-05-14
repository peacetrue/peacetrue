package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultType;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * default {@link ExceptionConverter} handle all {@link Exception}
 *
 * @author xiayx
 */
public class FallbackExceptionConverter extends AbstractExceptionConverter<Exception, Void> {

    @Override
    public Result convert(Exception exception, @Nullable Locale locale) {
        logger.warn("no matched ExceptionConverter found for {}, used the FallbackExceptionConverter", exception.getClass().getName());
        return super.convert(exception, locale);
    }

    @Override
    protected String getCode(Exception exception) {
        return ResultType.error.name();
    }

}
