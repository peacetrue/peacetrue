package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * for {@link MethodArgumentNotValidException}
 *
 * @author xiayx
 */
public class MethodArgumentNotValidExceptionConverter implements ExceptionConverter<MethodArgumentNotValidException> {

    private ExceptionConverter<BindException> bindExceptionConverter;

    @Override
    public Result convert(MethodArgumentNotValidException exception, @Nullable Locale locale) {
        BindExceptionConverter bindExceptionConverter = (BindExceptionConverter) this.bindExceptionConverter;
        return bindExceptionConverter.convert(exception.getBindingResult(), locale);
    }

    @Autowired
    public void setBindExceptionConverter(ExceptionConverter<BindException> bindExceptionConverter) {
        this.bindExceptionConverter = bindExceptionConverter;
    }
}
