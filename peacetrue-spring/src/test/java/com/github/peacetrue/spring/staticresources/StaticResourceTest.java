package com.github.peacetrue.spring.staticresources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author : xiayx
 * @since : 2020-07-04 11:33
 **/
@SpringBootTest(classes = {
        StaticResourceAutoConfiguration.class,
})
public class StaticResourceTest {

    @Autowired
    private StaticResourceProperties properties;
    @Autowired(required = false)
    private RouterFunction<ServerResponse> staticResourceLocator;

    @Test
    void propertiesIsOK() {
        Assertions.assertNotNull(properties);
        Assertions.assertTrue(properties.getStaticResources().isEmpty());
    }

    @Test
    void staticResourceLocatorIsNull() {
        Assertions.assertNull(staticResourceLocator);
    }

}
