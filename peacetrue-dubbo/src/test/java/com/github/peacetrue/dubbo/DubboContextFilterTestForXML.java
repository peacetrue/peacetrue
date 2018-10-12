package com.github.peacetrue.dubbo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest({
        "spring.profiles.active=consumer",
        "spring.config.location=classpath:/com/github/peacetrue/dubbo/"
})
@ContextConfiguration("classpath:dubbo-consumer.xml")
public class DubboContextFilterTestForXML {

    @Autowired
    private SomeService someService;
    @Autowired
    private Environment environment;

    @Test
    public void getConsumerApplicationName() throws Exception {
        Assert.assertEquals(
                environment.getProperty("dubbo.application.name"),
                someService.getConsumerApplicationName()
        );
    }

}