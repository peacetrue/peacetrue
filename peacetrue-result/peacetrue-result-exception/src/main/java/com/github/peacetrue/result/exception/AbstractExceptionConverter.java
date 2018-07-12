package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultBuilder;
import com.github.peacetrue.result.ResultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * a abstract {@link ExceptionConverter}
 *
 * @author xiayx
 */
public abstract class AbstractExceptionConverter<T extends Exception, D> implements ExceptionConverter<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected ResultBuilder resultBuilder;

    @Override
    public Result convert(T exception, @Nullable Locale locale) {
        D data = resolveData(exception);
        Object[] arguments = resolveArguments(exception, data);
        String code = resolveCode(exception);
        return data == null
                ? resultBuilder.build(code, arguments, locale)
                : resultBuilder.build(code, arguments, data, locale);
    }

    /**
     * get standard result code
     *
     * @param exception the exception
     * @return the standard result code
     */
    protected String resolveCode(T exception) {
        return exception.getClass().getSimpleName();
    }

    /**
     * convert data
     *
     * @param exception the exception
     * @return the data
     */
    @Nullable
    protected D resolveData(T exception) {
        return null;
    }

    /**
     * convert arguments
     *
     * @param exception the exception
     * @param data      the data
     * @return the arguments
     */
    @Nullable
    protected Object[] resolveArguments(T exception, D data) {
        return null;
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }

}
