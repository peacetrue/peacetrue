package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.Parameter;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.stream.Collectors;

/**
 * for {@link InvalidFormatException} in {@link HttpMessageNotReadableException}
 *
 * @author xiayx
 */
public class InvalidFormatExceptionConverter extends AbstractExceptionConverter<InvalidFormatException, Parameter<Class, Object>> {

    @Override
    protected Parameter<Class, Object> resolveData(InvalidFormatException exception) {
        Parameter<Class, Object> parameter = new Parameter<>();
        parameter.setName(exception.getPath().stream().map(this::getFieldName).collect(Collectors.joining(".")));
        parameter.setValue(exception.getValue());
        return parameter;
    }

    private String getFieldName(JsonMappingException.Reference reference) {
        return reference.getIndex() == -1 ? reference.getFieldName() : (reference.getFieldName() + "[" + reference.getIndex() + "]");
    }

    @Override
    protected Object[] resolveArguments(InvalidFormatException exception, Parameter<Class, Object> data) {
        return new Object[]{data.getName(), exception.getTargetType(), data.getValue()};
    }

}
