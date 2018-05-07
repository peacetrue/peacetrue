package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultBuilder;
import com.github.peacetrue.result.ResultCodeResolver;
import com.github.peacetrue.result.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * default {@link ExceptionConverter} handle all {@link Exception}
 *
 * @author xiayx
 */
public class DefaultExceptionConverter implements ExceptionConverter<Exception> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /** the custom result code */
    private String code;
    private ResultBuilder resultBuilder;

    @Override
    public Result convert(Exception exception, @Nullable Locale locale) {
        logger.warn("no matched ExceptionConverter found for {}, used the DefaultExceptionConverter", exception.getClass().getName());
        return resultBuilder.build(code, null, exception.getMessage(), locale);
    }

    @Autowired
    public void setResultCodeResolver(ResultCodeResolver resultCodeResolver) {
        this.code = resultCodeResolver.resolve(ResultType.error.name());
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }
}
