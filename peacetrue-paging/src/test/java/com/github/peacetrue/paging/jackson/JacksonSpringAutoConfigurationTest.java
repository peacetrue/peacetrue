package com.github.peacetrue.paging.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.peacetrue.paging.PagingAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * the tests for {@link PagingAutoConfiguration}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagingAutoConfiguration.class)
public class JacksonSpringAutoConfigurationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void convert() throws Exception {
        PageImpl<Object> page = new PageImpl<>(Collections.emptyList());
        ObjectNode objectNode = objectMapper.valueToTree(page);
        Assert.assertEquals(objectNode.get("total").asInt(), page.getTotalElements());
        Assert.assertEquals(objectNode.get("data").size(), page.getContent().size());
    }
}