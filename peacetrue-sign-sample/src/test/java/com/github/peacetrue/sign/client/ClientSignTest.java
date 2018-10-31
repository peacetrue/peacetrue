package com.github.peacetrue.sign.client;

import com.github.peacetrue.sign.SignUtils;
import com.github.peacetrue.sign.User;
import com.github.peacetrue.sign.append.SignAppendAutoConfiguration;
import com.github.peacetrue.sign.append.SignAppendProperties;
import com.github.peacetrue.sign.append.ClientRestTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.unitils.reflectionassert.ReflectionAssert;

/**
 * tests for client sign
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        SignAppendAutoConfiguration.class,
}, properties = "spring.profiles.active=client")
@AutoConfigureWebClient
public class ClientSignTest {

    @Autowired
    private ClientRestTemplate clientRestTemplate;
    @Autowired
    private SignAppendProperties signProperties;

    private User user = new User(1L, "姓名", "密码");

    @Test
    public void getParamSuccess() throws Exception {
        User response = clientRestTemplate.getForObject("?id={0}&name={1}&password={2}",
                User.class, user.getId(), user.getName(), user.getPassword());
        ReflectionAssert.assertReflectionEquals(user, response);
    }

    @Test
    public void getParamError() throws Exception {
        try {
            //手动设置错误的秘钥
            clientRestTemplate.getForObject("?id={0}&name={1}&password={2}&_appId=xiamen&_appSecret=12345",
                    User.class, user.getId(), user.getName(), user.getPassword());
            Assert.fail("must be error");
        } catch (RestClientException ignored) {
        }
    }


    @Test
    public void postParamSuccess() throws Exception {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("id", user.getId().toString());
        params.add("name", user.getName());
        params.add("password", user.getPassword());
        User response = clientRestTemplate.postForObject(params, User.class);
        ReflectionAssert.assertReflectionEquals(user, response);
    }

    @Test
    public void postParamError() throws Exception {
        try {
            //手动设置错误的秘钥
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("id", user.getId().toString());
            params.add("name", user.getName());
            params.add("password", user.getPassword());
            params.add(signProperties.getAppIdParamName(), signProperties.getClient().getAppId());
            params.add(signProperties.getAppSecretParamName(), signProperties.getClient().getAppSecret() + "7");
            User response = clientRestTemplate.postForObject(params, User.class);
            ReflectionAssert.assertReflectionEquals(user, response);
            Assert.fail("must be error");
        } catch (RestClientException ignored) {
        }
    }


    @Test
    public void bodySuccess() throws Exception {
        User response = clientRestTemplate.postForObject("/body", new HttpEntity<>(user), User.class);
        ReflectionAssert.assertReflectionEquals(user, response);
    }

    @Test
    public void bodyError() throws Exception {
        try {
            Object wrap = SignUtils.wrap(user, "xiamen", "12345");
            clientRestTemplate.postForObject("/body", new HttpEntity<>(wrap), User.class);
            Assert.fail("must be error");
        } catch (RestClientException ignored) {

        }
    }


}