package com.github.peacetrue.associate.jpa;

import com.github.peacetrue.associate.SameCollectionAssociatedSource;
import com.github.peacetrue.spring.util.BeanUtils;

import javax.persistence.EntityManager;

/**
 * @author xiayx
 */
public class JpaObjectCollectionAssociatedSource<I, R> extends JpaCollectionAssociatedSource<I, R, R> implements SameCollectionAssociatedSource<I, R> {

    private String idProperty = "id";

    public JpaObjectCollectionAssociatedSource() {
    }

    public JpaObjectCollectionAssociatedSource(Class<?> entityClass) {
        super(entityClass);
    }

    public JpaObjectCollectionAssociatedSource(EntityManager entityManager, Class<?> entityClass) {
        super(entityManager, entityClass);
    }

    public JpaObjectCollectionAssociatedSource(EntityManager entityManager, Class<?> entityClass, String idProperty) {
        super(entityManager, entityClass);
        this.idProperty = idProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public I resolveId(R data) {
        return (I) BeanUtils.getPropertyValue(data, idProperty);
    }

    public String getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }
}
