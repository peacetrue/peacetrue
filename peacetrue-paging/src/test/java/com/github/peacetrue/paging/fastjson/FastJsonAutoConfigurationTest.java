package com.github.peacetrue.paging.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.github.peacetrue.paging.PagingAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * the tests for {@link PagingAutoConfiguration}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagingAutoConfiguration.class)
public class FastJsonAutoConfigurationTest {

    @Autowired
    private FastJsonConfig fastJsonConfig;

    @Test
    public void convert() throws Exception {
        PageImpl<Object> page = new PageImpl<>(Collections.emptyList());
        System.out.println(JSON.toJSONString(page, fastJsonConfig.getSerializeConfig()));
    }
}