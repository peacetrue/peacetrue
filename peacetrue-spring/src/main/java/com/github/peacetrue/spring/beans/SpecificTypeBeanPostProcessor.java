package com.github.peacetrue.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.ResolvableType;

/**
 * {@link BeanPostProcessor} for specific type
 *
 * @author xiayx
 */
public abstract class SpecificTypeBeanPostProcessor<T> implements BeanPostProcessor {

    private Class<T> beanType;

    @SuppressWarnings("unchecked")
    public SpecificTypeBeanPostProcessor() {
        beanType = (Class<T>) ResolvableType.forClass(SpecificTypeBeanPostProcessor.class, this.getClass()).resolveGeneric(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanType.isAssignableFrom(bean.getClass())) {
            return postProcessBeforeInitializationInternal((T) bean, beanName);
        }
        return bean;
    }

    /**
     * internal implement for {@link #postProcessBeforeInitialization(Object, String)}
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws org.springframework.beans.BeansException in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     */
    protected Object postProcessBeforeInitializationInternal(T bean, String beanName) {
        return bean;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanType.isAssignableFrom(bean.getClass())) {
            return postProcessAfterInitializationInternal((T) bean, beanName);
        }
        return bean;

    }

    /**
     * internal implement for {@link #postProcessAfterInitialization(Object, String)}
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws org.springframework.beans.BeansException in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     * @see org.springframework.beans.factory.FactoryBean
     */
    protected Object postProcessAfterInitializationInternal(T bean, String beanName) {
        return bean;
    }

}
