package com.github.peacetrue.spring.entityclass;

import com.github.peacetrue.spring.symbolresolver.SymbolResolver;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * support to resolve {@link EntityClass}
 *
 * @author xiayx
 * @see EntityClass
 */
public class EntityClassHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private SymbolResolver<Class> symbolResolver;

    public EntityClassHandlerMethodArgumentResolver(SymbolResolver<Class> symbolResolver) {
        this.symbolResolver = symbolResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Class.class
                && parameter.getParameterAnnotation(EntityClass.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return symbolResolver.resolveSymbol(webRequest);
    }
}
