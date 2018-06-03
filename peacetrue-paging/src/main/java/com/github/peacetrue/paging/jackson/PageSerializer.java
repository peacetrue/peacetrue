package com.github.peacetrue.paging.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.peacetrue.paging.converter.PageConverter;

import java.io.IOException;
import java.util.Objects;

/**
 * {@link JsonSerializer} for page
 *
 * @author xiayx
 */
public class PageSerializer<T> extends JsonSerializer<T> {

    private PageConverter<T> pageConverter;

    public PageSerializer(PageConverter<T> pageConverter) {
        this.pageConverter = Objects.requireNonNull(pageConverter);
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Object page = pageConverter.convert(value);
        provider.defaultSerializeValue(page, gen);
    }

}