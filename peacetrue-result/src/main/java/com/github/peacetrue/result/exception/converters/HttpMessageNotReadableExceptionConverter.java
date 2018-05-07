package com.github.peacetrue.result.exception.converters;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.peacetrue.result.ErrorType;
import org.springframework.http.converter.HttpMessageNotReadableException;

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
            fieldErrorBean.setPropertyPath(invalidFormatException.getPathReference());
            fieldErrorBean.setInvalidValue(invalidFormatException.getValue());
            return fieldErrorBean;
        }
        return null;
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
