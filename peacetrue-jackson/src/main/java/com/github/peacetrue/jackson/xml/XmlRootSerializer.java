package com.github.peacetrue.jackson.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.IOException;

/**
 * 将 bean 作为 root 元素输出
 * <p>
 * <pre>
 * public class Bean1{
 *     private Bean2 body;
 * }
 * public class Bean2{
 *     private String name;
 * }
 *
 * xmlMapper.writeValueAsString(new Bean1(new Bean2("..")));
 * &lt;Bean1>
 *     &lt;body>
 *         &lt;name>..&lt;/name>
 *     &lt;/body>
 * &lt;/Bean1>
 *
 * public class Bean3{
 *     &nbsp;@JsonSerialize(using = XmlRootSerializer.class)
 *     private Bean2 body;
 * }
 *
 * xmlMapper.writeValueAsString(new Bean3(new Bean2("..")));
 * &lt;Bean1>
 *     &lt;body>
 *         &lt;Bean2>
 *              &lt;name>..&lt;/name>
 *         &lt;/Bean2>
 *     &lt;/body>
 * &lt;/Bean1>
 * </pre>
 *
 * @author xiayx
 */
public class XmlRootSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        if (value instanceof Iterable) {
            for (Object item : (Iterable) value) {
                writeProperty(gen, item);
            }
        } else if (value instanceof Object[]) {
            for (Object item : (Object[]) value) {
                writeProperty(gen, item);
            }
        } else {
            writeProperty(gen, value);
        }
        gen.writeEndObject();
    }

    protected void writeProperty(JsonGenerator gen, Object item) throws IOException {
        if (item == null) return;
        gen.writeFieldName(getFieldName(item));
        gen.writeObject(item);
    }

    protected String getFieldName(Object item) {
        Class<?> itemClass = item.getClass();
        JacksonXmlRootElement jacksonXmlRootElement = itemClass.getAnnotation(JacksonXmlRootElement.class);
        return jacksonXmlRootElement == null
                ? itemClass.getSimpleName()
                : jacksonXmlRootElement.localName();
    }

}
