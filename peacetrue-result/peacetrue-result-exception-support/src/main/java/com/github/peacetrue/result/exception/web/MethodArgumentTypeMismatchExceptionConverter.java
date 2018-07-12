package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.Parameter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * support {@link MethodArgumentTypeMismatchException}
 *
 * @author xiayx
 */
public class MethodArgumentTypeMismatchExceptionConverter extends AbstractExceptionConverter<MethodArgumentTypeMismatchException, Parameter<Class, Object>> {

    @Override
    protected Parameter<Class, Object> resolveData(MethodArgumentTypeMismatchException exception) {
        return new Parameter<>(exception.getName(), exception.getRequiredType(), exception.getValue());
    }

    @Override
    protected Object[] resolveArguments(MethodArgumentTypeMismatchException exception, Parameter<Class, Object> data) {
        return new Object[]{data.getName(), data.getType(), data.getValue()};
    }

}
