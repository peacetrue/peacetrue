package com.github.peacetrue.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * custom enum serializer
 *
 * @author xiayx
 */
public class EnumSerializer<T extends Enum<T>> extends JsonSerializer<T> {

    /** the alias of {@link Enum#ordinal} */
    private String ordinalAlias;
    /** the alias of {@link Enum#name} */
    private String nameAlias;

    public EnumSerializer() {
        this("ordinal", "name");
    }

    public EnumSerializer(String ordinalAlias, String nameAlias) {
        this.ordinalAlias = Objects.requireNonNull(ordinalAlias);
        this.nameAlias = Objects.requireNonNull(nameAlias);
    }

    public void serialize(T value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName(ordinalAlias);
        generator.writeNumber(value.ordinal());
        generator.writeFieldName(nameAlias);
        generator.writeString(value.name());
        generator.writeEndObject();
    }
}