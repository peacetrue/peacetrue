package com.github.peacetrue.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * a util class for Jackson
 *
 * @author xiayx
 */
public abstract class JacksonUtils {

//    public static final JavaType OBJECT_MAP_TYPE = TypeFactory.defaultInstance().constructParametrizedType(HashMap.class, Map.class, String.class, Object.class);
//
//    public static JavaType getObjectMapType() {
//        return OBJECT_MAP_TYPE;
//    }

    /**
     * similar to {@link ObjectMapper#writeValueAsString(Object)},
     * but convert the JsonProcessingException to RuntimeException
     *
     * @param objectMapper the objectMapper
     * @param source       the source
     * @return the json string source
     */
    public static String writeValueAsString(ObjectMapper objectMapper, Object source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
