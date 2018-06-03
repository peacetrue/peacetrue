package com.github.peacetrue.paging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.peacetrue.paging.converter.GenericPageConverter;
import com.github.peacetrue.paging.jackson.PageSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
public class GlobalPageConverterTest {


    @Test
    public void convert() throws Exception {
        GenericPageConverter converter = new GenericPageConverter();

        HashMap<PageAttribute, String> source = new HashMap<>();
        source.put(PageAttribute.data, "content");
        converter.setSource(source);
        Page page = new PageImpl<>(Collections.emptyList());
        Map<String, Object> convert = converter.convert(page);
        Assert.assertEquals(page.getTotalElements(), convert.get(GenericPageConverter.DEFAULT_SOURCE.get(PageAttribute.totalElements)));
        Assert.assertEquals(page.getContent(), convert.get("data"));
    }

    @Test
    public void jackson() throws Exception {
        GenericPageConverter converter = new GenericPageConverter();
        HashMap<PageAttribute, String> source = new HashMap<>();
        source.put(PageAttribute.data, "content");
        converter.setSource(source);

        Page page = new PageImpl<>(Collections.emptyList());

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Page.class, new PageSerializer<>(converter));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        String string = objectMapper.writeValueAsString(page);
        System.out.println(string);
    }


}