package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * for {@link Exception#getCause()}
 *
 * @author xiayx
 */
public class ProxyConditionalExceptionConverter implements ConditionalExceptionConverter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private List<Class<? extends Exception>> proxyClasses;
    private ExceptionConvertService exceptionConvertService;

    public ProxyConditionalExceptionConverter() {
    }

    public ProxyConditionalExceptionConverter(List<Class<? extends Exception>> proxyClasses) {
        this.proxyClasses = Objects.requireNonNull(proxyClasses);
    }

    @Override
    public boolean supports(Exception exception) {
        Throwable cause = exception.getCause();
        if (!(cause instanceof Exception) || cause.equals(exception)) return false;
        for (Class<? extends Exception> proxyClass : proxyClasses) {
            if (proxyClass.equals(exception.getClass())) {
                logger.info("the exception class '{}' proxy for '{}'", exception.getClass().getName(), cause.getClass().getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public Result convert(Exception exception, @Nullable Locale locale) {
        return exceptionConvertService.convert((Exception) exception.getCause(), locale);
    }

    public void setProxyClasses(List<Class<? extends Exception>> proxyClasses) {
        this.proxyClasses = proxyClasses;
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }
}
