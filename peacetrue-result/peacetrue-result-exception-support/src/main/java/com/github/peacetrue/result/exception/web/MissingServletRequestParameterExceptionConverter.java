package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.Parameter;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.annotation.Nullable;

/**
 * for {@link MissingServletRequestParameterException}
 *
 * @author xiayx
 */
public class MissingServletRequestParameterExceptionConverter
        extends AbstractExceptionConverter<MissingServletRequestParameterException, Parameter<String, String>> {

    @Nullable
    @Override
    protected Parameter<String, String> resolveData(MissingServletRequestParameterException exception) {
        return new Parameter<>(exception.getParameterName(), exception.getParameterType());
    }

    @Nullable
    @Override
    protected Object[] resolveArguments(MissingServletRequestParameterException exception, Parameter<String, String> data) {
        return new Object[]{data.getName(), data.getType()};
    }
}
