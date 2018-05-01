package com.github.peacetrue.spring.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * tests for {@link EnumUtils}
 *
 * @author xiayx
 */
public class EnumUtilsTest {

    public enum TestEnum {
        a("name of a"),
        b("name of b"),
        c("name of c");

        private String name;

        TestEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void mapForOwnProperty() throws Exception {
        Map<Integer, String> map = EnumUtils.map(TestEnum.values(), EnumUtils.PROPERTY_ORDINAL, EnumUtils.PROPERTY_NAME);
        Assert.assertEquals(TestEnum.a.name(), map.get(TestEnum.a.ordinal()));
        Assert.assertEquals(TestEnum.b.name(), map.get(TestEnum.b.ordinal()));
        Assert.assertEquals(TestEnum.c.name(), map.get(TestEnum.c.ordinal()));
    }

    @Test
    public void mapForCustomProperty() throws Exception {
        Map<Integer, String> map = EnumUtils.map(TestEnum.values(), EnumUtils.PROPERTY_ORDINAL, "name");
        Assert.assertEquals(TestEnum.a.getName(), map.get(TestEnum.a.ordinal()));
        Assert.assertEquals(TestEnum.b.getName(), map.get(TestEnum.b.ordinal()));
        Assert.assertEquals(TestEnum.c.getName(), map.get(TestEnum.c.ordinal()));

        Map<String, String> map1 = EnumUtils.map(TestEnum.values(), EnumUtils.PROPERTY_NAME, "name");
        Assert.assertEquals(TestEnum.a.getName(), map1.get(TestEnum.a.name()));
        Assert.assertEquals(TestEnum.b.getName(), map1.get(TestEnum.b.name()));
        Assert.assertEquals(TestEnum.c.getName(), map1.get(TestEnum.c.name()));
    }


}