package com.github.peacetrue.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * avoid checked exception
 *
 * @author xiayx
 */
public class ObjectMapperWrapper {

    private ObjectMapper objectMapper;
    private JsonTypeInfo.Id jsonTypeId = JsonTypeInfo.Id.CLASS;

    public ObjectMapperWrapper(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    /** similar to {@link ObjectMapper#writeValueAsString(Object)}, but avoid checked exception */
    public String writeValueAsString(Object source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new JacksonWriteException(e);
        }
    }

    /** similar to {@link ObjectMapper#writeValueAsString(Object)}, but avoid checked exception */
    public String writeValueAsString(Object source, JavaType javaType) {
        try {
            return objectMapper.writer().forType(javaType).writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new JacksonWriteException(e);
        }
    }

    /** similar to {@link ObjectMapper#readValue(String, JavaType)}, but avoid checked exception */
    @SuppressWarnings("unchecked")
    public <T> T readValue(String source, JavaType javaType) {
        try {
            return objectMapper.readValue(source, javaType);
        } catch (IOException e) {
            throw new JacksonReadException(e);
        }
    }

    /** write append type info for wrapper type */
    public String writeAppendType(@Nullable Object source) {
        if (source == null) return null;

        if (source instanceof Collection) {
            return ((Collection<?>) source).stream().map(this::writeAppendType).collect(Collectors.joining(",", "[", "]"));
        } else if (source instanceof Object[]) {
            return Arrays.stream((Object[]) source).map(this::writeAppendType).collect(Collectors.joining(",", "[", "]"));
        } else if (BeanUtils.isSimpleValueType(source.getClass())) {
            return writeValueAsString(source);
        } else {
            Map<String, Object> map = objectMapper.convertValue(source, JacksonUtils.OBJECT_MAP_TYPE);
            map.put(jsonTypeId.getDefaultPropertyName(), source.getClass().getName());
            return writeValueAsString(map);
        }
    }

    /** read auto detect type info for wrapper type */
    public Object readDetectType(@Nullable String source) {
        if (source == null) return null;
        Object object = this.readValue(source, objectMapper.getTypeFactory().constructType(Object.class));
        return convertValue(object);
    }

    private Object convertValue(Object object) {
        if (object instanceof Collection) {
            return ((Collection<?>) object).stream().map(this::convertValue).collect(Collectors.toList());
        } else if (object instanceof Object[]) {
            return Arrays.stream((Object[]) object).map(this::convertValue).collect(Collectors.toList());
        } else if (object instanceof Map) {
            return this.convertValue((Map) object);
        } else {
            return object;
        }
    }

    private Object convertValue(Map map) {
        String propertyName = jsonTypeId.getDefaultPropertyName();
        String className = (String) map.remove(propertyName);
        if (className == null) return map;
        try {
            Class<?> toValueType = Class.forName(className);
            return objectMapper.convertValue(map, toValueType);
        } catch (ClassNotFoundException e) {
            throw new MapTypeParseException(className, e);
        }
    }

}
