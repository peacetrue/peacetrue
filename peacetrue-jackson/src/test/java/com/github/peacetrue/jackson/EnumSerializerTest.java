package com.github.peacetrue.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * @author xiayx
 */
public class EnumSerializerTest {

    public enum RandomEnum {
        A("a"), B("b"), C("c");

        private String id;

        RandomEnum(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
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