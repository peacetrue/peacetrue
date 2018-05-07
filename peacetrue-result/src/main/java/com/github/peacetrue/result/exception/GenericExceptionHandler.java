package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ErrorPageResolver;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.exception.converters.ExceptionConverter;
import com.github.peacetrue.spring.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

//import javax.persistence.PersistenceException;

/**
 * handle all {@link Exception} throw by {@link Controller}
 *
 * @author xiayx
 */
@ControllerAdvice
public class GenericExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());


    private ExceptionConverter<Exception> genericExceptionConverter;
    private ErrorPageResolver errorPageResolver;

    @ExceptionHandler(Exception.class)
    public String handleAll(Exception exception, Model model, HttpServletRequest request) {
        if (!logger.isDebugEnabled()) logger.error("handle Exception", exception);
        Result result = genericExceptionConverter.convert(exception);
        model.addAllAttributes(BeanUtils.map(result));
        return errorPageResolver.resolve(request, exception);
    }

    @Autowired
    public void setGenericExceptionConverter(ExceptionConverter<Exception> genericExceptionConverter) {
        this.genericExceptionConverter = genericExceptionConverter;
    }

    @Autowired
    public void setErrorPageResolver(ErrorPageResolver errorPageResolver) {
        this.errorPageResolver = errorPageResolver;
    }

    //        return "errors/404";
    //        model.addAllAttributes(BeanUtils.map(NotFound.concrete(ex.getRequestURL())));
    //    public String handle404(NoHandlerFoundException ex, Model model) {
//    @ExceptionHandler(NoHandlerFoundException.class)

//    }


    //    @ExceptionHandler(BindException.class)
//    public String handleBindException(BindException ex, Model model) {
//        ex.getAllErrors()
//        model.addAllAttributes(BeanUtils.map(exceptionConverter.convert(ex)));
//        return "errors/all";
//    }
//    @ExceptionHandler(PersistenceException.class)
//    public Object handle(PersistenceException ex) {
//        Map<String, Object> result = new LinkedHashMap<>();
//        result.put("code", "persistenceException");
//        Throwable rootCause = NestedExceptionUtils.getRootCause(ex);
//        if (rootCause == null) rootCause = ex;
//        result.put("message", rootCause.getLocalizedMessage());
//        return result;
//    }


}