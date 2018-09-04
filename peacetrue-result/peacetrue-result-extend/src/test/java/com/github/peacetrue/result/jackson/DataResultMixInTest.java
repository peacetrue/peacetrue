package com.github.peacetrue.result.jackson;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.DataResultImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiayx
 */
public class DataResultMixInTest {


    @Data
    @AllArgsConstructor
    public static class Bean1 {
        interface View1 {}

        interface View2 extends DataResultView {}

        @JsonView({View1.class, View2.class})
        private String name;
    }

    @Test
    public void name() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(DataResult.class, DataResultMixIn.class);
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        DataResultImpl<Object> result = new DataResultImpl<>("code", "message", new Bean1("1"));
        String value = objectMapper.writerWithView(Bean1.View1.class).writeValueAsString(result);
        Assert.assertEquals("{}", value);

        value = objectMapper.writerWithView(Bean1.View2.class).writeValueAsString(result);
        Assert.assertEquals("{\"code\":\"code\",\"message\":\"message\",\"data\":{\"name\":\"1\"}}", value);

        result.setData(null);
        value = objectMapper.writerWithView(Bean1.View2.class).writeValueAsString(result);
        Assert.assertEquals("{\"code\":\"code\",\"message\":\"message\",\"data\":null}", value);
    }
}