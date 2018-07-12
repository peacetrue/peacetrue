package com.github.peacetrue.spring.entityclass;

import com.github.peacetrue.jpa.MetamodelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * the jpa entity class resolver
 *
 * @author xiayx
 */
public class JpaEntityClassResolver implements EntityClassResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Class resolveEntityClass(String entityName) {
        Class entityClass = MetamodelUtils.getEntityClass(entityManager.getMetamodel(), entityName);
        logger.debug("got entity class '{}' for entity name '{}'", entityClass == null ? null : entityClass.getName(), entityName);
        return entityClass;
    }
}
