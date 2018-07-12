package com.github.peacetrue.spring.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.Objects;

/**
 * holder a bean factory
 *
 * @author xiayx
 */
public class BeanFactoryHolder implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = Objects.requireNonNull(beanFactory);
    }

    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
