package com.github.peacetrue.result;

import com.github.peacetrue.spring.beans.SpecificTypeBeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * used to replace {@link JsonViewResponseBodyAdvice} to {@link ResultResponseBodyAdvice}
 *
 * @author xiayx
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#requestMappingHandlerAdapter
 */
public class ResultBeanPostProcessor extends SpecificTypeBeanPostProcessor<RequestMappingHandlerAdapter> {

    private ResultResponseBodyAdvice resultResponseBodyAdvice;

    public ResultBeanPostProcessor(ResultResponseBodyAdvice resultResponseBodyAdvice) {
        this.resultResponseBodyAdvice = Objects.requireNonNull(resultResponseBodyAdvice);
    }

    @Override
    protected Object postProcessBeforeInitializationInternal(RequestMappingHandlerAdapter bean, String beanName) {
        Field field = ReflectionUtils.findField(RequestMappingHandlerAdapter.class, "requestResponseBodyAdvice");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Object> bodyAdvices = (List) ReflectionUtils.getField(field, bean);
        for (int i = 0; i < bodyAdvices.size(); i++) {
            Object requestBodyAdvice = bodyAdvices.get(i);
            if (requestBodyAdvice instanceof JsonViewResponseBodyAdvice) {
                resultResponseBodyAdvice.setResponseBodyAdvice((JsonViewResponseBodyAdvice) requestBodyAdvice);
                bodyAdvices.set(i, resultResponseBodyAdvice);
            }
        }
        return super.postProcessBeforeInitializationInternal(bean, beanName);
    }

}
