package com.github.peacetrue.result.exception.converters;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.peacetrue.result.ErrorType;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.stream.Collectors;

/**
 * for {@link HttpMessageNotReadableException}
 *
 * @author xiayx
 */
public class HttpMessageNotReadableExceptionConverter extends AbstractExceptionConverter<HttpMessageNotReadableException, FieldErrorBean> {

    @Override
    protected FieldErrorBean resolveData(HttpMessageNotReadableException exception) {
        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) exception.getCause();
            FieldErrorBean fieldErrorBean = new FieldErrorBean();
            fieldErrorBean.setPropertyPath(invalidFormatException.getPath().stream().map(this::getFieldName).collect(Collectors.joining(".")));
            fieldErrorBean.setInvalidValue(invalidFormatException.getValue());
            return fieldErrorBean;
        }
        return null;
    }

    private String getFieldName(JsonMappingException.Reference reference) {
        return reference.getIndex() == -1 ? reference.getFieldName() : (reference.getFieldName() + "[" + reference.getIndex() + "]");
    }

    @Override
    protected Object[] resolveArguments(HttpMessageNotReadableException exception, FieldErrorBean data) {
        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) exception.getCause();
            return new Object[]{data.getPropertyPath(), data.getInvalidValue(), invalidFormatException.getTargetType()};
        }
        return null;
    }

    @Override
    protected String getCode() {
        return ErrorType.argument_type_mismatch.name();
    }

}
