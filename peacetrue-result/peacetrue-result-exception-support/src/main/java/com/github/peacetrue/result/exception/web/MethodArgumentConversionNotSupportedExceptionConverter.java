package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.Parameter;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

/**
 * support {@link MethodArgumentConversionNotSupportedException}
 *
 * @author xiayx
 */
public class MethodArgumentConversionNotSupportedExceptionConverter
        extends AbstractExceptionConverter<MethodArgumentConversionNotSupportedException, Parameter<Class, Object>> {

    @Override
    protected Parameter<Class, Object> resolveData(MethodArgumentConversionNotSupportedException exception) {
        return new Parameter<>(exception.getName(), exception.getRequiredType(), exception.getValue());
    }

    @Override
    protected Object[] resolveArguments(MethodArgumentConversionNotSupportedException exception, Parameter<Class, Object> data) {
        return new Object[]{data.getName(), data.getType(), data.getValue()};
    }

}
