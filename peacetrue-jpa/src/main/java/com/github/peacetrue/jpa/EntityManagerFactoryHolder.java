package com.github.peacetrue.jpa;

import javax.persistence.EntityManagerFactory;
import java.util.Objects;

/**
 * holder a {@link EntityManagerFactory}
 *
 * @author xiayx
 */
public class EntityManagerFactoryHolder {

    private static EntityManagerFactory entityManagerFactory;

    /**
     * set entityManagerFactory
     *
     * @param entityManagerFactory the entityManagerFactory
     */
    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        EntityManagerFactoryHolder.entityManagerFactory = Objects.requireNonNull(entityManagerFactory);
    }

    /**
     * get entityManagerFactory
     *
     * @return the entityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

}
