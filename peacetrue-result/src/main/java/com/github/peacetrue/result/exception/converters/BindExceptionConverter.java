package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * support {@link BindException}
 *
 * @author xiayx
 */
public class BindExceptionConverter implements ExceptionConverter<BindException> {

    private String code;
    private ResultCodeResolver resultCodeResolver;
    private ResultBuilder resultBuilder;

    @Override
    public Result convert(BindException exception, Locale locale) {
        return convert((BindingResult) exception, locale);
    }

    /**
     * convert BindingResult to Result
     *
     * @param bindingResult the bindingResult
     * @param locale        the locale
     * @return the Result
     */
    protected Result convert(BindingResult bindingResult, Locale locale) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        if (errors.size() == 1) return map(errors.get(0));
        List<Result> results = errors.stream().map(this::map).collect(Collectors.toList());
        return resultBuilder.build(code, new Object[]{bindingResult.getErrorCount()}, results, locale);
    }

    /**
     * convert objectError to Result
     *
     * @param objectError the objectError
     * @return the Result
     */
    protected Result map(ObjectError objectError) {
//        ConstraintViolation violation = objectError.unwrap(ConstraintViolation.class);
//        FieldErrorBean fieldErrorBean = new FieldErrorBean();
//        fieldErrorBean.setPropertyPath(objectError.getObjectName());
//        fieldErrorBean.setInvalidValue(violation.getInvalidValue());
//        String code = resultCodeResolver.resolve(violation.getConstraintDescriptor().getAnnotation());
//        String message = fieldErrorBean.getPropertyPath() + violation.getMessage();
//        return new DataResultImpl<>(code, message, fieldErrorBean);
        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            FieldErrorBean fieldErrorBean = new FieldErrorBean();
            fieldErrorBean.setPropertyPath(fieldError.getField());
            fieldErrorBean.setInvalidValue(fieldError.getRejectedValue());
            return new DataResultImpl<>(resultCodeResolver.resolve(fieldError.getCode()), fieldError.getDefaultMessage(), fieldErrorBean);
        } else {
            //no data available
            return new ResultImpl(resultCodeResolver.resolve(objectError.getCode()), objectError.getDefaultMessage());
        }
    }


    @Autowired
    public void setResultCodeResolver(ResultCodeResolver resultCodeResolver) {
        this.resultCodeResolver = resultCodeResolver;
        this.code = resultCodeResolver.resolve(ResultType.error.group());
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }
}
