package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * 无参的异常转换器，使用异常的类名作为异常编码
 *
 * @author xiayx
 */
public class NoArgsConditionalExceptionConverter implements ConditionalExceptionConverter {

    @Autowired
    private ResultBuilder resultBuilder;
    private Set<Class<? extends Exception>> exceptionClasses = new HashSet<>();

    public NoArgsConditionalExceptionConverter(Set<Class<? extends Exception>> exceptionClasses) {
        this.exceptionClasses = Objects.requireNonNull(exceptionClasses);
    }

    @Override
    public boolean supports(Exception exception) {
        return exceptionClasses.contains(exception.getClass());
    }

    @Override
    public Result convert(Exception exception, @Nullable Locale locale) {
        return resultBuilder.build(exception.getClass().getSimpleName(), locale);
    }
}
