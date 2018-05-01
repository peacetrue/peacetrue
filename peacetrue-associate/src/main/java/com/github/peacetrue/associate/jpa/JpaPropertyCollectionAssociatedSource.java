package com.github.peacetrue.associate.jpa;

import com.github.peacetrue.associate.CollectionAssociatedSource;

import javax.persistence.EntityManager;

/**
 * @author xiayx
 */
public class JpaPropertyCollectionAssociatedSource<I, R> extends JpaCollectionAssociatedSource<I, Object[], R> implements CollectionAssociatedSource<I, Object[], R> {

    private String idProperty = "id";
    private String associatedProperty;

    public JpaPropertyCollectionAssociatedSource() {
    }

    public JpaPropertyCollectionAssociatedSource(Class<?> entityClass) {
        super(entityClass);
    }

    public JpaPropertyCollectionAssociatedSource(EntityManager entityManager, Class<?> entityClass) {
        super(entityManager, entityClass);
    }

    public JpaPropertyCollectionAssociatedSource(EntityManager entityManager, Class<?> entityClass, String idProperty, String associatedProperty) {
        super(entityManager, entityClass);
        this.idProperty = idProperty;
        this.associatedProperty = associatedProperty;
    }

    @Override
    protected String getSelect(String entityAlias) {
        return entityAlias + "." + idProperty + ", " + entityAlias + "." + associatedProperty;
    }

    @SuppressWarnings("unchecked")
    public I resolveId(Object[] data) {
        return (I) data[0];
    }

    @SuppressWarnings("unchecked")
    public R format(Object[] data) {
        return (R) data[1];
    }

    public String getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }

    public String getAssociatedProperty() {
        return associatedProperty;
    }

    public void setAssociatedProperty(String associatedProperty) {
        this.associatedProperty = associatedProperty;
    }
}
