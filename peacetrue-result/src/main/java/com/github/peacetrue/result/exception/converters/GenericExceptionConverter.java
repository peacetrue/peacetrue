package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.spring.util.GenericParameterTypeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Optional;

/**
 * a Generic {@link ExceptionConverter}
 *
 * @author xiayx
 */
public class GenericExceptionConverter
        extends GenericParameterTypeProxy<ExceptionConverter>
        implements ExceptionConverter<Exception> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Class<ExceptionConverter> getElementClass() {
        return ExceptionConverter.class;
    }

    @SuppressWarnings("unchecked")
    public Result convert(Exception exception, Locale locale) {
        Optional<ExceptionConverter> converter = find(exception.getClass());
        if (converter.isPresent()) {
            ExceptionConverter exceptionConverter = converter.get();
            logger.debug("delegate to {}", exceptionConverter.getClass().getName());
            return exceptionConverter.convert(exception, locale);
        }
        return null;
    }
}
