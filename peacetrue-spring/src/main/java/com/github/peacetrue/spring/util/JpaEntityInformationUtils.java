package com.github.peacetrue.spring.util;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

/**
 * support cache for {@link JpaEntityInformation}
 *
 * @author xiayx
 * @see JpaEntityInformationSupport
 */
public class JpaEntityInformationUtils {

    private final static Map<Class<?>, JpaEntityInformation<?, ?>> JPA_ENTITY_INFORMATION = new HashMap<>();

    /**
     * get JpaEntityInformation with cache support
     *
     * @param domainClass must not be {@literal null}
     * @param em          must not be {@literal null}
     * @param <T>         the type of domain class
     * @return never {@literal null}
     * @see JpaEntityInformationSupport#getEntityInformation(Class, EntityManager)
     */
    @SuppressWarnings("unchecked")
    public static <T> JpaEntityInformation<T, ?> getEntityInformation(Class<T> domainClass, EntityManager em) {
        return (JpaEntityInformation<T, ?>) JPA_ENTITY_INFORMATION.computeIfAbsent(domainClass, c -> JpaEntityInformationSupport.getEntityInformation(c, em));
    }

}
