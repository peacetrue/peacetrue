package com.github.peacetrue.dubbo.multipleregistry;

import com.github.peacetrue.dubbo.multipleregistry.provider1.Provider1Service;
import com.github.peacetrue.dubbo.multipleregistry.provider2.Provider2Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest({
        "spring.profiles.active=consumer",
        "spring.config.location=classpath:/com/github/peacetrue/dubbo/multipleregistry/"
})
@ContextConfiguration(locations = "classpath:com/github/peacetrue/dubbo/multipleregistry/consumer.xml")
public class ConsumerXml {

    @Autowired
    private Provider1Service provider1Service;
    @Autowired
    private Provider2Service provider2Service;

    @Test
    public void doSomething() throws Exception {
        String something = "something";
        Assertions.assertEquals(something, provider1Service.doSomething(something));
        Assertions.assertEquals(something, provider2Service.doSomething(something));
    }

}
