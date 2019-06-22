package com.github.peacetrue.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 实体信息找不到
 *
 * @author xiayx
 */
public class EntityNotFoundException extends RuntimeException {

    private Class<?> entityClass;
    private String[] propertyNames;
    private Object[] propertyValues;

    public EntityNotFoundException(Class<?> entityClass, String propertyNames, Object propertyValues) {
        this(entityClass, new String[]{propertyNames}, new Object[]{propertyValues});
    }

    public EntityNotFoundException(Class<?> entityClass, String[] propertyNames, Object[] propertyValues) {
        super(String.format("the entity[%s] with condition[%s=%s] not found",
                entityClass.getName(),
                String.join("&", propertyNames),
                Arrays.stream(propertyValues).map(Object::toString).collect(Collectors.joining("&"))
        ));
        this.entityClass = entityClass;
        this.propertyNames = propertyNames;
        this.propertyValues = propertyValues;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

    public Object[] getPropertyValues() {
        return propertyValues;
    }
}