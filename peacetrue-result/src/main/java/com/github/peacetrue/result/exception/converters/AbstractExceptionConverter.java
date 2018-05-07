package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
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
        logger.info("convert {} to Result", exception.getClass().getName());
        D data = resolveData(exception);
        Object[] arguments = resolveArguments(exception, data);
        return resultBuilder.build(getCode(), arguments, data, locale);
    }

    /**
     * get standard result code
     *
     * @return the standard result code
     */
    protected abstract String getCode();

    /**
     * resolve data
     *
     * @param exception the exception
     * @return the data
     */
    protected abstract D resolveData(T exception);

    /**
     * resolve arguments
     *
     * @param exception the exception
     * @param data      the data
     * @return the arguments
     */
    protected abstract Object[] resolveArguments(T exception, D data);

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }
}
