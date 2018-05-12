package com.github.peacetrue.result.exception.converters.jackson;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.peacetrue.result.ErrorType;
import com.github.peacetrue.result.exception.converters.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.converters.FieldErrorBean;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.stream.Collectors;

/**
 * for {@link InvalidFormatException} in {@link HttpMessageNotReadableException}
 *
 * @author xiayx
 */
public class InvalidFormatExceptionConverter extends AbstractExceptionConverter<InvalidFormatException, FieldErrorBean> {

    @Override
    protected FieldErrorBean resolveData(InvalidFormatException exception) {
        FieldErrorBean fieldErrorBean = new FieldErrorBean();
        fieldErrorBean.setPropertyPath(exception.getPath().stream().map(this::getFieldName).collect(Collectors.joining(".")));
        fieldErrorBean.setInvalidValue(exception.getValue());
        return fieldErrorBean;
    }

    private String getFieldName(JsonMappingException.Reference reference) {
        return reference.getIndex() == -1 ? reference.getFieldName() : (reference.getFieldName() + "[" + reference.getIndex() + "]");
    }

    @Override
    protected Object[] resolveArguments(InvalidFormatException exception, FieldErrorBean data) {
        return new Object[]{data.getPropertyPath(), data.getInvalidValue(), exception.getTargetType()};
    }

    @Override
    protected String getCode() {
        return ErrorType.argument_type_mismatch.name();
    }

}
