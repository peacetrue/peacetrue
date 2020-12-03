package com.github.peacetrue.sign.appender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.spring.util.BeanUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
}, properties = "server.port=8082")
public class ValidatorTest {

    @Autowired
    private AppenderRestTemplate appenderRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static Appender request = new Appender();

    static {
        request.setId(1L);
        request.setName("hello world");
    }

    private void assertThat(DataResultImpl response) {
        if (response.getCode().equals(ResultType.success.name())) {
            ReflectionAssert.assertReflectionEquals(request, objectMapper.convertValue(response.getData(), Appender.class));
        } else {
            Assertions.assertEquals("000002", response.getCode());
        }
    }

    @Test
    public void getFormSuccess() throws Exception {
        DataResultImpl response = appenderRestTemplate.getForObject("/validator/form?id={0}&name={1}", DataResultImpl.class, request.getId(), request.getName());
        assertThat(response);
    }


    @Test
    public void postFormSuccess() throws Exception {
        Map<String, Object> params_ = BeanUtils.map(request);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.setAll(params_);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params);
        DataResultImpl response = appenderRestTemplate.postForObject("/validator/form", httpEntity, DataResultImpl.class);
        assertThat(response);
    }

    @Test
    public void postBodySuccess() throws Exception {
        HttpEntity<Appender> httpEntity = new HttpEntity<>(request);
        DataResultImpl response = appenderRestTemplate.postForObject("/validator/body", httpEntity, DataResultImpl.class);
        assertThat(response);
    }

}
