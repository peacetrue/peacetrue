package com.github.peacetrue.paging.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.github.peacetrue.paging.converter.PageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * {@link PageSerializer} for page
 *
 * @author xiayx
 */
public class PageSerializer implements ObjectSerializer {

    private PageConverter<Object> pageConverter;

    public PageSerializer(PageConverter<Object> pageConverter) {
        this.pageConverter = Objects.requireNonNull(pageConverter);
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.write(pageConverter.convert(object));
    }
}