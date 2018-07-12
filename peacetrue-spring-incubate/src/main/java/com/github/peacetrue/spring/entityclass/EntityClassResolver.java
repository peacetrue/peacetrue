package com.github.peacetrue.spring.entityclass;


import javax.annotation.Nullable;

/**
 * the entity class resolver
 *
 * @author xiayx
 */
public interface EntityClassResolver {

    /**
     * resolve entity class from entity name
     *
     * @param entityName must not be {@literal null}
     * @return maybe {@literal null}
     */
    @Nullable
    Class resolveEntityClass(String entityName);
}
