package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.Parameter;
import org.springframework.web.bind.MissingPathVariableException;

import javax.annotation.Nullable;

/**
 * for {@link MissingPathVariableException}
 *
 * @author xiayx
 */
public class MissingPathVariableExceptionConverter
        extends AbstractExceptionConverter<MissingPathVariableException, Parameter<Class, Object>> {

    @Nullable
    @Override
    protected Parameter<Class, Object> resolveData(MissingPathVariableException exception) {
        return new Parameter<>(exception.getVariableName(), exception.getParameter().getParameterType());
    }

    @Nullable
    @Override
    protected Object[] resolveArguments(MissingPathVariableException exception, Parameter<Class, Object> data) {
        return new Object[]{data.getName(), data.getType()};
    }

}
