package com.github.peacetrue.sign.client;

import com.github.peacetrue.sign.SignUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * tests for client sign
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class ClientSignTest {

    private String url = "http://localhost:8090/user";
    @Autowired
    private RestTemplate userConsumerRestTemplate;
    @Autowired
    private RestTemplate providerRestTemplate;

    @Test
    public void postAsConsumer() throws Exception {
        User user = new User("name", "password");
        HttpEntity<Object> httpEntity = new HttpEntity<>(SignUtils.wrap(user));
        User response = userConsumerRestTemplate.postForObject(url, httpEntity, User.class);
        Assert.assertEquals(user.getName(), response.getName());
        Assert.assertEquals(user.getPassword(), response.getPassword());
    }

    @Test
    public void postAsProvider() throws Exception {
        User user = new User("name", "password");
        HttpEntity<Object> httpEntity = new HttpEntity<>(SignUtils.wrap(user, "xiamen"));
        User response = providerRestTemplate.postForObject(url, httpEntity, User.class);
        System.out.println(response);
        Assert.assertEquals(user.getName(), response.getName());
        Assert.assertEquals(user.getPassword(), response.getPassword());
    }
}