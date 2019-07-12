package com.github.peacetrue.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiayx
 */
public class ObjectMapperWrapperTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    public static class A {
        private String b;
        private String a;
    }

    private ObjectMapperWrapper wrapper = new ObjectMapperWrapper(new ObjectMapper());

    @Test
    public void writeValueAsString() {
        String s = wrapper.writeValueAsString(new A("b", "a"));
        Assert.assertEquals(s, "{\"@class\":\"com.github.peacetrue.jackson.ObjectMapperWrapperTest$A\",\"b\":\"b\",\"a\":\"a\"}");
    }

    @Test
    public void readValue() {
        A source = new A("b", "a");
        String s = wrapper.writeValueAsString(source);
        Object o = wrapper.readValue(s);
        Assert.assertEquals(source, o);
    }

    @Test
    public void readValue1() {
    }
}