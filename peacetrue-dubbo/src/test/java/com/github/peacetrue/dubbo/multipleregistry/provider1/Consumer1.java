package com.github.peacetrue.dubbo.multipleregistry.provider1;

import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest({
        "spring.profiles.active=consumer1",
        "spring.config.location=classpath:/com/github/peacetrue/dubbo/multipleregistry/provider1/"
})
public class Consumer1 {

    @Reference(registry = "provider1")
    private Provider1Service provider1Service;

    @Test
    public void doSomething() throws Exception {
        String something = "something";
        Assert.assertEquals(something, provider1Service.doSomething(something));
    }

}