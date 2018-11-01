package com.github.peacetrue.result;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiayx
 */
@RestController
public class ExceptionTestController {

    /*缺少参数*/
    @RequestMapping("/MissingServletRequestParameterException")
    public Object MissingServletRequestParameterException(@RequestParam String name) {
        return name;
    }

    @RequestMapping("/MissingPathVariableException")
    public Object MissingPathVariableException(@PathVariable Integer number) {
        return number;
    }

    /*参数类型错误*/
    @RequestMapping("/MethodArgumentTypeMismatchException")
    public Object MethodArgumentTypeMismatchException(Integer number) {
        return number;
    }

    @RequestMapping("/MethodArgumentTypeMismatchException/{number}")
    public Object MethodArgumentTypeMismatchException1(@PathVariable Integer number) {
        return number;
    }

    @RequestMapping("/MethodArgumentConversionNotSupportedException")
    public Object MethodArgumentConversionNotSupportedException(@RequestParam User user) {
        return user;
    }

    /*参数范围错误*/
    @RequestMapping("/BindException")
    public Object BindException(@Validated User user) {
        return user;
    }

}
