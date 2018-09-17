package com.github.peacetrue.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.github.peacetrue.jackson.xml.XmlRootDeserializer;
import com.github.peacetrue.jackson.xml.XmlRootSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author xiayx
 */
public class XmlRootSerializerTest {

    private XmlMapper xmlMapper;

    public XmlRootSerializerTest() {
        xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setDefaultUseWrapper(false);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bean1<T> {
        private T body;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bean2 {
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bean3<T> {
        @JsonSerialize(using = XmlRootSerializer.class)
        @JsonDeserialize(using = XmlRootDeserializer.class)
        private T body;
    }

    @Test
    public void xmlBodySerializer() throws Exception {
        String string4 = xmlMapper.writeValueAsString(new Bean1<>(new Bean2("name")));
        Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<Bean1>\n" +
                "  <body>\n" +
                "    <name>name</name>\n" +
                "  </body>\n" +
                "</Bean1>\n", string4);

        String string50 = xmlMapper.writeValueAsString(new Bean3<>(new Bean2("name")));
        Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<Bean3>\n" +
                "  <body>\n" +
                "    <Bean2>\n" +
                "      <name>name</name>\n" +
                "    </Bean2>\n" +
                "  </body>\n" +
                "</Bean3>\n", string50);

        String string5 = xmlMapper.writeValueAsString(new Bean3<>(Arrays.asList(new Bean2("name"), new Bean2("name"))));
        Assert.assertEquals("<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<Bean3>\n" +
                "  <body>\n" +
                "    <Bean2>\n" +
                "      <name>name</name>\n" +
                "    </Bean2>\n" +
                "    <Bean2>\n" +
                "      <name>name</name>\n" +
                "    </Bean2>\n" +
                "  </body>\n" +
                "</Bean3>\n", string5);


        XmlRootDeserializer.addBeanClass(Bean2.class);
        Bean3 bean3 = xmlMapper.readValue(string50, Bean3.class);
        System.out.println(bean3);
    }

}
