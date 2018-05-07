package com.github.peacetrue.spring.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * generic parameter service
 *
 * @param <T> the type of concrete service
 */
public abstract class GenericParameterTypeProxy<T> {

    protected List<T> elements = new ArrayList<>();
    protected Map<Class, T> elementMap;

    @PostConstruct
    public void init() {
        if (getElementClass().isAssignableFrom(this.getClass())) elements.remove(this);
        elementMap = GenericParameterUtils.map(elements, getElementClass(), getGenericParameterIndex());
    }

    /**
     * find matched service
     *
     * @param targetClass the target class
     * @return matched service
     */
    protected Optional<T> find(Class targetClass) {
        return GenericParameterUtils.find(elementMap, targetClass);
    }

    /**
     * get element class
     *
     * @return element class
     */
    protected abstract Class<T> getElementClass();

    /**
     * get generic parameter index
     *
     * @return generic parameter index
     */
    protected int getGenericParameterIndex() {return 0;}

    public List<T> getElements() {
        return elements;
    }

    @Autowired(required = false)
    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}