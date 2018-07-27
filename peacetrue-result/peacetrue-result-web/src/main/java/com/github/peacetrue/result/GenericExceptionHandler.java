package com.github.peacetrue.result;

import com.github.peacetrue.result.exception.ExceptionConvertService;
import com.github.peacetrue.spring.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * handle all {@link Exception} throw by {@link Controller}
 *
 * @author xiayx
 */
@ControllerAdvice
public class GenericExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ExceptionConvertService exceptionConvertService;
    private ExceptionPageResolver exceptionPageResolver;

    /**
     * handle all exception
     *
     * @param exception the exception
     * @param model     the model
     * @param request   the request
     * @return the failure page
     */
    @ExceptionHandler(Exception.class)
    public String handleAll(Exception exception, Model model, HttpServletRequest request) {
        if (!logger.isDebugEnabled()) logger.warn("handle Exception", exception);
        Result result = exceptionConvertService.convert(exception);
        model.addAllAttributes(BeanUtils.map(result));
        return exceptionPageResolver.resolve(request, exception);
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }

    @Autowired
    public void setErrorPageResolver(ExceptionPageResolver exceptionPageResolver) {
        this.exceptionPageResolver = exceptionPageResolver;
    }

}