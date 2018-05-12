package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.ErrorType;
import com.github.peacetrue.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * for {@link HttpMessageNotReadableException}
 *
 * @author xiayx
 */
public class HttpMessageNotReadableExceptionConverter extends AbstractExceptionConverter<HttpMessageNotReadableException, String> {

    private ExceptionConverter<Exception> genericExceptionConverter;

    @Override
    public Result convert(HttpMessageNotReadableException exception, @Nullable Locale locale) {
        Exception cause = (Exception) exception.getCause();
        if (cause == null || cause == exception) return super.convert(exception, locale);
        return genericExceptionConverter.convert(cause, locale);
    }

    @Override
    protected String getCode() {
        return ErrorType.argument_error.name();
    }

    @Nullable
    @Override
    protected String resolveData(HttpMessageNotReadableException exception) {
        return exception.getMessage();
    }

    @Autowired
    public void setGenericExceptionConverter(ExceptionConverter<Exception> genericExceptionConverter) {
        this.genericExceptionConverter = genericExceptionConverter;
    }
}
