package com.github.peacetrue.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.*;

/**
 * a util class for Jackson
 *
 * @author xiayx
 */
public abstract class JacksonUtils {
    /** the object map type */
    public static final JavaType OBJECT_MAP_TYPE = TypeFactory.defaultInstance()
            .constructParametrizedType(LinkedHashMap.class, Map.class, String.class, Object.class);
    public static final JavaType LIST_OBJECT_MAP_TYPE = TypeFactory.defaultInstance()
            .constructParametrizedType(List.class, ArrayList.class, OBJECT_MAP_TYPE);


    /** parse java type from object instance */
    public static JavaType parseJavaType(Object object) {
        return parseJavaTypeInner(Objects.requireNonNull(object));
    }

    /** parse java type from object instance. generic type not supported */
    private static JavaType parseJavaTypeInner(Object object) {
        if (object == null) return TypeFactory.unknownType();
        if (object instanceof Collection) {
            Collection<?> collection = (Collection) object;
            JavaType elementType = collection.isEmpty() ? TypeFactory.unknownType() : parseJavaTypeInner(collection.iterator().next());
            return TypeFactory.defaultInstance().constructCollectionType(collection.getClass(), elementType);
        } else if (object instanceof Object[]) {
            Object[] objects = (Object[]) object;
            JavaType elementType = objects.length == 0 ? TypeFactory.unknownType() : parseJavaTypeInner(objects[0]);
            return TypeFactory.defaultInstance().constructArrayType(elementType);
        }
        return TypeFactory.defaultInstance().constructType(object.getClass());
    }
}