package com.github.peacetrue.spring.staticresources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author : xiayx
 * @since : 2020-07-04 11:33
 **/
@SpringBootTest(classes = {
        StaticResourceAutoConfiguration.class,
})
@ActiveProfiles("exist")
public class StaticResourceExistTest {

    @Autowired
    private StaticResourceProperties properties;
    @Autowired(required = false)
    private RouterFunction<ServerResponse> staticResourceLocator;

    @Test
    void propertiesIsOK() {
        Assertions.assertNotNull(properties);
        Assertions.assertEquals(2, properties.getStaticResources().size());
    }

    @Test
    void staticResourceLocatorNotNull() {
        Assertions.assertNotNull(staticResourceLocator);
    }


}
