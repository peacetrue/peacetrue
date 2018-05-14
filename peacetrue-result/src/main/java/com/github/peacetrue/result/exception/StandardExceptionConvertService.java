package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.ErrorType;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.exception.converters.ConditionalExceptionConverter;
import com.github.peacetrue.result.exception.converters.ExceptionConverter;
import com.github.peacetrue.spring.util.GenericParameterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * the standard {@link ExceptionConvertService}
 *
 * @author xiayx
 */
public class StandardExceptionConvertService implements ExceptionConvertService {

    /** Well-known name for the fallback ExceptionConverter object in the bean factory for this namespace. */
    public static final String FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME = "fallbackExceptionConverter";

    private Logger logger = LoggerFactory.getLogger(getClass());
    private List<ExceptionConverter> exceptionConverters;
    private Map<Class, ExceptionConverter> exceptionConverterMap;
    private List<ConditionalExceptionConverter> conditionalExceptionConverters;
    private ExceptionConverter<Exception> fallbackExceptionConverter;

    @PostConstruct
    public void init() {
        exceptionConverterMap = GenericParameterUtils.map(exceptionConverters, ExceptionConverter.class, 0);
        //remove FallbackExceptionConverter if exists
        exceptionConverterMap.remove(Exception.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result convert(Exception exception, @Nullable Locale locale) {
        try {
            Optional<ExceptionConverter> exceptionConverter = GenericParameterUtils.find(exceptionConverterMap, exception.getClass());
            if (exceptionConverter.isPresent()) return exceptionConverter.get().convert(exception, locale);

            for (ConditionalExceptionConverter conditionalExceptionConverter : conditionalExceptionConverters) {
                if (conditionalExceptionConverter.supports(exception)) {
                    return conditionalExceptionConverter.convert(exception, locale);
                }
            }

            return fallbackExceptionConverter.convert(exception, locale);
        } catch (Exception e) {
            //Make sure there are no exceptions
            logger.warn("exception occurs when convert exception[{}] to result", exception.getClass().getName(), e);
            String message = "When you see this exception indicates that the exception convert system has problems, developers need to fix it.";
            return new DataResultImpl<>(ErrorType.exception_converter_error.name(), message, exception.getMessage());
        }
    }

    @Autowired
    public void setExceptionConverters(List<ExceptionConverter> exceptionConverters) {
        this.exceptionConverters = exceptionConverters;
    }

    @Autowired
    public void setConditionalExceptionConverters(List<ConditionalExceptionConverter> conditionalExceptionConverters) {
        this.conditionalExceptionConverters = conditionalExceptionConverters;
    }

    @Autowired
    public void setFallbackExceptionConverter(ExceptionConverter<Exception> fallbackExceptionConverter) {
        this.fallbackExceptionConverter = fallbackExceptionConverter;
    }
}
