package com.github.peacetrue.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * @author xiayx
 */
public class EnumSerializerTest {

    public enum RandomEnum {
        A("a", "name1"), B("b", "name2"), C("c", "name3");

        private String id;
        private String name;

        RandomEnum(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void customSerialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Enum.class, EnumMixIn.CustomSerializer.class);
        String string = objectMapper.writeValueAsString(RandomEnum.values());
        System.out.println(string);
    }

    @Test
    public void objectSerialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Enum.class, EnumMixIn.ObjectSerializer.class);
        String string = objectMapper.writeValueAsString(RandomEnum.values());
        System.out.println(string);
    }


}