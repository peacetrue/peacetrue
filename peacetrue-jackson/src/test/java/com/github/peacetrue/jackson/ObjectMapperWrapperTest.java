package com.github.peacetrue.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author xiayx
 */
public class ObjectMapperWrapperTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class A {
        private String b;
        private String a;
    }

    private ObjectMapperWrapper wrapper = new ObjectMapperWrapper(new ObjectMapper());

    @Test
    public void writeAppendType() {
        String s = wrapper.writeAppendType(new A("b", "a"));
        Assert.assertEquals(s, "{\"b\":\"b\",\"a\":\"a\",\"@class\":\"com.github.peacetrue.jackson.ObjectMapperWrapperTest$A\"}");
    }

    @Test
    public void readDetectType4Simple() {
        Object source = "a";
        String s = wrapper.writeAppendType(source);
        Object o = wrapper.readDetectType(s);
        Assert.assertEquals(source, o);
    }

    @Test
    public void readDetectType4Wrapper() {
        Object source = new A("b", "a");
        String s = wrapper.writeAppendType(source);
        Object o = wrapper.readDetectType(s);
        Assert.assertEquals(source, o);
    }

    @Test
    public void readDetectType4ListSimple() {
        Object source = Arrays.asList("a", "b");
        String s = wrapper.writeAppendType(source);
        Object o = wrapper.readDetectType(s);
        Assert.assertEquals(source, o);
    }

    @Test
    public void readDetectType4ListWrapper() {
        List<A> source = Arrays.asList(new A("b", "a"), new A("b", "a"), new A("b", "a"));
        String s = wrapper.writeAppendType(source);
        Object o = wrapper.readDetectType(s);
        Assert.assertEquals(source, o);
    }

    @Test
    public void readDetectType4ListWrapperAsSimple() {
        Object source = Arrays.asList(new Date(), new Date());
        String s = wrapper.writeAppendType(source);
        Object o = wrapper.readDetectType(s);
        Assert.assertNotEquals(source, o);
    }

}