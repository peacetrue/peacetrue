package com.github.peacetrue.result;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
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
 * Convert return data to {@link Result}
 *
 * @author xiayx
 */
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /** 使用 {@link WithoutResultWrapper} 替代 */
    @Deprecated
    private Set<Class> excludes = Collections.emptySet();
    private ResultBuilder resultBuilder;
    private ResponseBodyAdvice<Object> responseBodyAdvice;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return responseBodyAdvice.supports(returnType, converterType) || supportsInternal(returnType, converterType);
    }

    /**
     * Abstract template method that supports the given controller method return type. Invoked from {@link #supports(MethodParameter, Class)}.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked, {@code false} otherwise
     */
    protected boolean supportsInternal(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        return !returnType.hasMethodAnnotation(WithoutResultWrapper.class)
                && !Result.class.isAssignableFrom(parameterType)
                && !exclude(parameterType)
                && ResolvableType.forClass(HttpMessageConverter.class, converterType).resolveGeneric(0).isAssignableFrom(Result.class);
    }

    /**
     * Whether the return type should be excluded
     *
     * @param returnType the return type
     * @return {@code true} if return type should be excluded, {@code false} otherwise
     */
    protected boolean exclude(Class<?> returnType) {
        return excludes.stream().anyMatch(aClass -> ((Class<?>) aClass).isAssignableFrom(returnType));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!returnType.hasMethodAnnotation(WithoutResultWrapper.class) && !(body instanceof Result)) {
            body = body == null ? resultBuilder.success() : resultBuilder.success(body);
        }
        if (responseBodyAdvice instanceof JsonViewResponseBodyAdvice
                && returnType.getMethodAnnotation(JsonView.class) == null) return body;
        return responseBodyAdvice.beforeBodyWrite(body, returnType, selectedContentType, selectedConverterType, request, response);
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
