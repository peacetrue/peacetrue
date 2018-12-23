package com.github.peacetrue.sign.appender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.spring.util.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.Map;

/**
 * tests for client sign
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        SignAppenderAutoConfiguration.class,
})
public class AppenderTest {

    @Autowired
    private AppenderRestTemplate appenderRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static Appender request = new Appender();

    static {
        request.setId(1L);
        request.setName("hello world");
    }

    @Test
    public void getFormSuccess() throws Exception {
        Appender response = appenderRestTemplate.getForObject("/appender/form?id={0}&name={1}", Appender.class, request.getId(), request.getName());
        ReflectionAssert.assertReflectionEquals(request, response);
    }


    @Test
    public void postFormSuccess() throws Exception {
        Map<String, Object> params_ = BeanUtils.map(request);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.setAll(params_);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params);
        Appender response = appenderRestTemplate.postForObject("/appender/form", httpEntity, Appender.class);
        ReflectionAssert.assertReflectionEquals(request, response);
    }

    @Test
    public void postBodySuccess() throws Exception {
        HttpEntity<Appender> httpEntity = new HttpEntity<>(request);
        Map response = appenderRestTemplate.postForObject("/appender/body", httpEntity, Map.class);
        ReflectionAssert.assertReflectionEquals(objectMapper.writeValueAsString(request), response.get("data"));
    }

}