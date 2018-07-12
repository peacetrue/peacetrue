package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.FailureType;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultBuilder;
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
public class FallbackExceptionConverter implements ExceptionConverter<Exception> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ResultBuilder resultBuilder;

    @Override
    public Result convert(Exception exception, @Nullable Locale locale) {
        logger.warn("no matched ExceptionConverter found for {}, used the FallbackExceptionConverter", exception.getClass().getName());
        return resultBuilder.build(FailureType.server_error.name(), buildMessage(exception), locale);
    }

    protected String buildMessage(Exception exception) {
        return exception.getClass().getName() + "[" + exception.getMessage() + "]";
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }
}
