package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.ErrorType;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * support {@link MethodArgumentTypeMismatchException}
 *
 * @author xiayx
 */
public class MethodArgumentTypeMismatchExceptionConverter extends AbstractExceptionConverter<MethodArgumentTypeMismatchException, FieldErrorBean> {

    @Override
    protected FieldErrorBean resolveData(MethodArgumentTypeMismatchException exception) {
        FieldErrorBean fieldError = new FieldErrorBean();
        fieldError.setPropertyPath(exception.getName());
        fieldError.setInvalidValue(exception.getValue());
        return fieldError;
    }

    @Override
    protected Object[] resolveArguments(MethodArgumentTypeMismatchException exception, FieldErrorBean data) {
        return new Object[]{data.getPropertyPath(), data.getInvalidValue(), exception.getParameter().getParameterType()};
    }

    @Override
    protected String getCode() {
        return ErrorType.argument_type_mismatch.name();
    }

}
