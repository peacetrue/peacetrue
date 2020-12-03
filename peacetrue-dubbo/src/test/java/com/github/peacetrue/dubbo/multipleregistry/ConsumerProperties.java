package com.github.peacetrue.dubbo.multipleregistry;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.peacetrue.dubbo.multipleregistry.provider1.Provider1Service;
import com.github.peacetrue.dubbo.multipleregistry.provider2.Provider2Service;
import com.github.peacetrue.dubbo.multipleregistry.providerconsumer.ProviderConsumerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest({
        "spring.profiles.active=consumer",
        "spring.config.location=classpath:/com/github/peacetrue/dubbo/multipleregistry/"
})
public class ConsumerProperties {

    @Reference(registry = "provider1")
    private Provider1Service provider1Service;
    @Reference(registry = "provider2")
    private Provider2Service provider2Service;
//    @Reference(registry = "providerConsumer")
//    private ProviderConsumerService providerConsumerService;

    @Test
    public void doSomething() throws Exception {
        String something = "something";
        Assertions.assertEquals(something, provider1Service.doSomething(something));
        Assertions.assertEquals(something, provider2Service.doSomething(something));
//        Assertions.assertEquals(something, providerConsumerService.doSomething(something));
    }

}
