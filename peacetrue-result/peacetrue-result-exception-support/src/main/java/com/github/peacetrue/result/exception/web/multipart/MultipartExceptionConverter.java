package com.github.peacetrue.result.exception.web.multipart;

import com.github.peacetrue.result.FailureType;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import com.github.peacetrue.result.exception.SimplifiedExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartException;

import javax.annotation.Nullable;
import java.util.Locale;

public class MultipartExceptionConverter extends SimplifiedExceptionConverter<MultipartException> {

    @Autowired
    private ExceptionConvertService exceptionConvertService;

    @Override
    public Result convert(MultipartException exception, @Nullable Locale locale) {
        if (exception.getMessage().startsWith("Current request is not a multipart request")) {
            return super.convert(exception, locale);
        } else if (exception.getMessage().startsWith("Could not parse multipart servlet request")) {
            return exceptionConvertService.convert((Exception) exception.getCause());
        }
        return resultBuilder.build(FailureType.server_error.name(), exception.getMessage(), locale);
    }
}