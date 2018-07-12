package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultBuilder;
import com.github.peacetrue.result.exception.ExceptionConverter;
import com.github.peacetrue.result.exception.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(BindExceptionConverter.class);
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
//        if (errors.size() == 1) return map(errors.get(0));
        List<Result> results = errors.stream().map(BindExceptionConverter::map).collect(Collectors.toList());
        return resultBuilder.build(BindException.class.getSimpleName(), new Object[]{bindingResult.getErrorCount()}, results, locale);
    }

    /**
     * convert objectError to Result
     *
     * @param objectError the objectError
     * @return the Result
     */
    public static Result map(ObjectError objectError) {
        logger.debug("convert '{}' to result", objectError);
        Parameter<Class, Object> parameter = new Parameter<>();
        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            parameter.setName(fieldError.getField());
            parameter.setValue(fieldError.getRejectedValue());
            return new DataResultImpl<>(fieldError.getCode(), fieldError.getDefaultMessage(), parameter);
        } else {
            //no data available
            parameter.setName(objectError.getObjectName());
            return new DataResultImpl<>(objectError.getCode(), objectError.getDefaultMessage(), parameter);
        }
    }

//    public static Result map(ConstraintViolation violation) {
//        Parameter<Class,Object> parameter = new Parameter<>();
//        parameter.setName(violation.getPropertyPath().toString());
//        return new DataResultImpl<>(violation.get, message, parameter);
//    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }
}
