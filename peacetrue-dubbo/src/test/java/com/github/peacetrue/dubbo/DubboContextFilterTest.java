package com.github.peacetrue.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest({
        "spring.profiles.active=consumer",
        "spring.config.location=classpath:/com/github/peacetrue/dubbo/"
})
public class DubboContextFilterTest {

    @Reference
    private SomeService someService;
    @Autowired
    private Environment environment;

    @Test
    public void getConsumerApplicationName() throws Exception {
        Assertions.assertEquals(
                environment.getProperty("dubbo.application.name"),
                someService.getConsumerApplicationName()
        );
    }

}
