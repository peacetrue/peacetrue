package com.github.peacetrue.enums;

import org.junit.Test;

import java.util.Set;

/**
 * @author xiayx
 */
public class EnumAutoConfigurationTest {

    public enum Anonymity {
        key1 {
            public Integer getType() {
                return null;
            }
        },
        key2 {
            public Integer getType() {
                return null;
            }
        };

        public abstract Integer getType();
    }

    @Test
    public void name() throws Exception {
        Set<Class<? extends Enum>> enumClasses = EnumAutoConfiguration.findEnumClasses("com.github.peacetrue");
        System.out.println(enumClasses);
    }
}