package com.github.peacetrue.jackson.xml;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 将 root 元素作为 bean 解析
 *
 * @author xiayx
 * @see XmlRootSerializer
 */
public class XmlRootDeserializer extends JsonDeserializer<Object> {

    //TODO how to add instance property instead of static property
    //TODO how to get current deserializer class instead of special
    public static final Map<String, Class> BEAN_CLASSES = new HashMap<>();

    public static void addBeanClass(Map<String, Class> beanClasses) {
        BEAN_CLASSES.putAll(beanClasses);
    }

    public static void addBeanClass(String beanName, Class<?> beanClass) {
        BEAN_CLASSES.put(beanName, beanClass);
    }

    public static void addBeanClass(Class<?>... beanClasses) {
        for (Class<?> beanClass : beanClasses) {
            addBeanClass(beanClass.getSimpleName(), beanClass);
        }
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (p.currentToken() == JsonToken.START_OBJECT) {

            String beanName = p.nextFieldName();
            if (beanName == null) return null;

            Class<?> beanClass = BEAN_CLASSES.get(beanName);
            if (beanClass == null) throw new JsonParseException(p, "bean name '" + beanName + "' is not unrecognized");

            if (p.nextToken() == JsonToken.START_OBJECT) return p.readValueAs(beanClass);
        }
        throw new JsonParseException(p, "expect a object token");
    }
}
