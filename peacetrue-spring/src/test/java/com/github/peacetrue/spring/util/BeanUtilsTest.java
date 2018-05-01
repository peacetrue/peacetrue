package com.github.peacetrue.spring.util;

import com.github.peacetrue.util.AssertUtils;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * tests for {@link BeanUtils}
 *
 * @author xiayx
 */
public class BeanUtilsTest {

    @Data
    public static class TestBean implements Cloneable {

        public static final List<String> PROPERTY_NAMES;

        static {
            try {
                PROPERTY_NAMES = Arrays.stream(Introspector.getBeanInfo(TestBean.class, Object.class).getPropertyDescriptors())
                        .map(PropertyDescriptor::getName).collect(Collectors.toList());
            } catch (IntrospectionException e) {
                throw new RuntimeException(e);
            }
        }

        private Long id;
        private String name;
        private String password;
        private String state;

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public static final Random RANDOM = new Random();
    public static final TestBean RANDOM_BEAN = getRandomValue();
    public static final List<TestBean> RANDOM_BEANS = IntStream.range(0, 10).mapToObj(value -> getRandomValue()).collect(Collectors.toList());

    private static void setRandomValue(TestBean testBean) {
        testBean.id = RANDOM.nextLong();
        testBean.name = RandomStringUtils.random(RANDOM.nextInt(10));
        testBean.password = RandomStringUtils.random(RANDOM.nextInt(10));
        testBean.state = RandomStringUtils.random(RANDOM.nextInt(10));
    }

    private static TestBean getRandomValue() {
        TestBean testBean = new TestBean();
        setRandomValue(testBean);
        return testBean;
    }

    @Test
    public void getPropertyDescriptors() {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(TestBean.class);
        List<String> actualFieldNames = Arrays.stream(propertyDescriptors).map(PropertyDescriptor::getName).collect(Collectors.toList());
        Assert.assertEquals(TestBean.PROPERTY_NAMES, actualFieldNames);
    }

    @Test
    public void getPropertyDescriptorsExcludeObjectGetClass() throws Exception {
        PropertyDescriptor[] propertyDescriptors = org.springframework.beans.BeanUtils.getPropertyDescriptors(TestBean.class);
        Assert.assertTrue(Arrays.stream(propertyDescriptors).map(FeatureDescriptor::getName).anyMatch(s -> s.equals("class")));
        propertyDescriptors = BeanUtils.getPropertyDescriptors(TestBean.class);
        Assert.assertFalse(Arrays.stream(propertyDescriptors).map(FeatureDescriptor::getName).anyMatch(s -> s.equals("class")));
    }

    @Test
    public void getRequiredPropertyDescriptor() throws Exception {
        //exists property id, no exception
        BeanUtils.getRequiredPropertyDescriptor(TestBean.class, "id");
        AssertUtils.assertException(() -> BeanUtils.getRequiredPropertyDescriptor(TestBean.class, "propertyNotExists"));
    }

    @Test
    public void getPropertyNames() throws Exception {
        List<String> actualFieldNames = Arrays.asList(BeanUtils.getPropertyNames(TestBean.class));
        Assert.assertEquals(TestBean.PROPERTY_NAMES, actualFieldNames);
    }

    @Test
    public void getPropertyValue() throws Exception {
        Assert.assertEquals(RANDOM_BEAN.id, BeanUtils.getPropertyValue(RANDOM_BEAN, "id"));
        Assert.assertEquals(RANDOM_BEAN.name, BeanUtils.getPropertyValue(RANDOM_BEAN, "name"));
        Assert.assertEquals(RANDOM_BEAN.password, BeanUtils.getPropertyValue(RANDOM_BEAN, "password"));
        Assert.assertEquals(RANDOM_BEAN.state, BeanUtils.getPropertyValue(RANDOM_BEAN, "state"));
        AssertUtils.assertException(() -> BeanUtils.getPropertyValue(RANDOM_BEAN, "propertyNotExists"));
    }

    @Test
    public void setPropertyValue() throws Exception {
        TestBean actual = new TestBean();
        BeanUtils.setPropertyValue(actual, "id", RANDOM_BEAN.id);
        BeanUtils.setPropertyValue(actual, "name", RANDOM_BEAN.name);
        BeanUtils.setPropertyValue(actual, "password", RANDOM_BEAN.password);
        BeanUtils.setPropertyValue(actual, "state", RANDOM_BEAN.state);
        Assert.assertEquals(RANDOM_BEAN.id, actual.id);
        Assert.assertEquals(RANDOM_BEAN.name, actual.name);
        Assert.assertEquals(RANDOM_BEAN.password, actual.password);
        Assert.assertEquals(RANDOM_BEAN.state, actual.state);
    }

    @Test
    public void map() throws Exception {
        Map<String, Object> map = BeanUtils.map(RANDOM_BEAN);
        Assert.assertEquals(RANDOM_BEAN.id, map.get("id"));
        Assert.assertEquals(RANDOM_BEAN.name, map.get("name"));
        Assert.assertEquals(RANDOM_BEAN.state, map.get("state"));
        Assert.assertEquals(RANDOM_BEAN.password, map.get("password"));
    }

    @Test
    public void mapIncludePropertyName() throws Exception {
        Map<String, Object> map = BeanUtils.map(RANDOM_BEAN, s -> s.equals("id"));
        Assert.assertEquals(RANDOM_BEAN.id, map.get("id"));
        Assert.assertFalse(map.containsKey("name"));
        Assert.assertFalse(map.containsKey("password"));
        Assert.assertFalse(map.containsKey("state"));
    }

    @Test
    public void mapIncludeProperty() throws Exception {
        Map<String, Object> map = BeanUtils.map(RANDOM_BEAN, (s, v) -> v != null && v.getClass().equals(String.class));
        Assert.assertEquals(RANDOM_BEAN.name, map.get("name"));
        Assert.assertEquals(RANDOM_BEAN.password, map.get("password"));
        Assert.assertEquals(RANDOM_BEAN.state, map.get("state"));
        Assert.assertFalse(map.containsKey("id"));
    }

    @Test
    public void mapCollection() throws Exception {
        Map<Long, String> idName = BeanUtils.map(RANDOM_BEANS, "id", "name");
        RANDOM_BEANS.forEach(testBean -> Assert.assertEquals(testBean.name, idName.get(testBean.id)));

        Map<Long, TestBean> idBean = BeanUtils.map(RANDOM_BEANS, "id");
        RANDOM_BEANS.forEach(testBean -> Assert.assertEquals(testBean, idBean.get(testBean.id)));

    }

}