package com.github.peacetrue.spring.web.method.support;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 具体类型参数解析器
 *
 * @author xiayx
 * @see ServletModelAttributeMethodProcessor
 */
public class ConcreteClassArgumentResolver extends ServletModelAttributeMethodProcessor {

    private Map<Class, Class> concreteClasses = new HashMap<>();
    private HandlerMethodArgumentConsumer<?> consumer;

    public ConcreteClassArgumentResolver(Map<Class, Class> concreteClasses) {
        this(concreteClasses, null);
    }

    public ConcreteClassArgumentResolver(Map<Class, Class> concreteClasses, HandlerMethodArgumentConsumer<?> consumer) {
        super(false);
        this.concreteClasses.putAll(Objects.requireNonNull(concreteClasses));
        this.consumer = consumer;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return concreteClasses.keySet().contains(parameter.getParameterType());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        String value = getRequestValueForAttribute(attributeName, request);
        if (value != null) {
            Object attribute = createAttributeFromRequestValue(value, attributeName, parameter, binderFactory, request);
            if (attribute != null) return attribute;
        }
        Object instantiate = BeanUtils.instantiateClass(concreteClasses.get(parameter.getParameterType()));
        if (consumer != null) ((Consumer) consumer).accept(instantiate);
        return instantiate;
    }
}
