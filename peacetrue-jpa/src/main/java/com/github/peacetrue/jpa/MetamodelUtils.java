package com.github.peacetrue.jpa;


import javax.annotation.Nullable;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.Type;
import java.util.Optional;

/**
 * a util class for {@link Metamodel}
 *
 * @author xiayx
 */
public abstract class MetamodelUtils {

    /**
     * get entity class by entity name with {@link String#equalsIgnoreCase}
     * <p>
     * NOTE: same entity name (ignore case) will make trouble, you should avoid that
     */
    @Nullable
    public static Class getEntityClass(Metamodel metamodel, String entityName) {
        return metamodel.getEntities().stream()
                .filter(entityType -> entityType.getName().equalsIgnoreCase(entityName))
                .findAny().map(Type::getJavaType).orElse(null);
    }

    /** get entity name by entity class with {@link Class#equals(Object)} */
    @Nullable
    public static String getEntityName(Metamodel metamodel, Class<?> entityClass) {
        return _getEntityName(metamodel, entityClass).orElse(null);
    }

    /** get entity name by entity class with {@link Class#equals(Object)} */
    public static String getRequiredEntityName(Metamodel metamodel, Class<?> entityClass) {
        return _getEntityName(metamodel, entityClass).orElseThrow(() -> new IllegalArgumentException(entityClass.getName() + " is a invalid entity class"));
    }

    private static Optional<String> _getEntityName(Metamodel metamodel, Class<?> entityClass) {
        return metamodel.getEntities().stream()
                .filter(entityType -> entityType.getJavaType().equals(entityClass))
                .findAny().map(EntityType::getName);
    }


}
