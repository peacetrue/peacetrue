package com.github.peacetrue.result;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Convert data to {@link Result}
 *
 * @author xiayx
 */
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private Set<Class> excludes = Collections.emptySet();
    private ResultBuilder resultBuilder;
    private ResponseBodyAdvice<Object> responseBodyAdvice;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !Result.class.isAssignableFrom(returnType.getParameterType()) || responseBodyAdvice.supports(returnType, converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof Result) && !exclude(body)) body = resultBuilder.build(body);
        if (responseBodyAdvice instanceof JsonViewResponseBodyAdvice
                && returnType.getMethodAnnotation(JsonView.class) == null) return body;
        return responseBodyAdvice.beforeBodyWrite(body, returnType, selectedContentType, selectedConverterType, request, response);
    }

    protected boolean exclude(Object body) {
        Class<?> bodyClass = body == null ? Void.class : body.getClass();
        return excludes.stream().anyMatch(aClass -> ((Class<?>) aClass).isAssignableFrom(bodyClass));
    }

    public void setExcludes(Set<Class> excludes) {
        this.excludes = Objects.requireNonNull(excludes);
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }

    public void setResponseBodyAdvice(ResponseBodyAdvice<Object> responseBodyAdvice) {
        this.responseBodyAdvice = responseBodyAdvice;
    }
}
