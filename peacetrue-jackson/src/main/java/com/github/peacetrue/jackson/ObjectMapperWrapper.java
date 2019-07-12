package com.github.peacetrue.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * avoid checked exception
 *
 * @author xiayx
 */
public class ObjectMapperWrapper {

    /** the object map type */
    public static final JavaType OBJECT_MAP_TYPE = TypeFactory.defaultInstance()
            .constructParametrizedType(LinkedHashMap.class, Map.class, String.class, Object.class);

    private ObjectMapper objectMapper;
    private JsonTypeInfo.Id jsonTypeId = JsonTypeInfo.Id.CLASS;

    public ObjectMapperWrapper(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public String writeValueAsString(Object source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new JacksonWriteException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T readValue(String source, JavaType javaType) {
        try {
            return objectMapper.readValue(source, javaType);
        } catch (IOException e) {
            throw new JacksonReadException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T readValue(String source) {
        try {
            Map<String, Object> map = objectMapper.readValue(source, OBJECT_MAP_TYPE);
            Class<?> toValueType = Class.forName((String) map.get(jsonTypeId.getDefaultPropertyName()));
            return (T) objectMapper.convertValue(map, toValueType);
        } catch (IOException | ClassNotFoundException e) {
            throw new JacksonReadException(e);
        }
    }


}
