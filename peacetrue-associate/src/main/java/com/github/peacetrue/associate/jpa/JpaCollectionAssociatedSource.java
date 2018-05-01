package com.github.peacetrue.associate.jpa;

import com.github.peacetrue.associate.CollectionAssociatedSource;
import com.github.peacetrue.jpa.MetamodelUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * a jpa implement for {@link CollectionAssociatedSource}
 *
 * @author xiayx
 */
@SuppressWarnings("unchecked")
public abstract class JpaCollectionAssociatedSource<I, D, R> implements CollectionAssociatedSource<I, D, R> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class entityClass;

    public JpaCollectionAssociatedSource() {
    }

    public JpaCollectionAssociatedSource(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public JpaCollectionAssociatedSource(EntityManager entityManager, Class<?> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    public Collection<D> getCollectionById(Collection<I> ids) {
        String entityName = MetamodelUtils.getEntityName(entityManager.getMetamodel(), entityClass);
        String qlString = "select " + getSelect("e") + " from " + entityName + " e where e.id in ?1";
        return entityManager.createQuery(qlString).setParameter(1, ids).getResultList();
    }

    /**
     * get select statement
     *
     * @param entityAlias the alias of entity
     * @return select statement
     */
    protected String getSelect(String entityAlias) {
        return entityAlias;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }
}
