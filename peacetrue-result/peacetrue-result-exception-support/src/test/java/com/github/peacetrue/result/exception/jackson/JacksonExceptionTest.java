package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.mysql.User;
import com.github.peacetrue.result.exception.Parameter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * test for jackson exception
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                MessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                ResultJacksonExceptionAutoConfiguration.class
        },
        properties = {
                "logging.level.root=info",
                "peacetrue.result.codes.InvalidFormatException=argument_format_mismatch",
        }
)
@SuppressWarnings("unchecked")
public class JacksonExceptionTest {

    @Autowired
    private ExceptionConvertService exceptionConvertService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MessageSource messageSource;


    @Test
    public void jsonParseExceptionConverter() throws Exception {
        try {
            objectMapper.readValue("{id:1}", User.class);
            Assert.fail();
        } catch (IOException e) {
            Assert.assertTrue(e instanceof JsonParseException);
            DataResult<Object[]> result = (DataResult<Object[]>) exceptionConvertService.convert(e);
            Assert.assertEquals("JsonParseException", result.getCode());
            Assert.assertEquals(
                    messageSource.getMessage("Result." + result.getCode(), result.getData(), LocaleContextHolder.getLocale()),
                    result.getMessage()
            );
        }

        try {
            objectMapper.readValue("{\"id\":a}", User.class);
            Assert.fail();
        } catch (IOException e) {
            Assert.assertTrue(e instanceof JsonParseException);
            DataResult<Object[]> result = (DataResult<Object[]>) exceptionConvertService.convert(e);
            Assert.assertEquals("JsonParseException", result.getCode());
            Assert.assertEquals(
                    messageSource.getMessage("Result." + result.getCode(), result.getData(), LocaleContextHolder.getLocale()),
                    result.getMessage()
            );
        }

    }

    @Test
    public void invalidFormatExceptionConverter() throws Exception {
        try {
            objectMapper.readValue("{\"createdTime\":\"a\"}", User.class);
            Assert.fail();
        } catch (IOException e) {
            Assert.assertTrue(e instanceof InvalidFormatException);
            DataResult<Parameter> result = (DataResult<Parameter>) exceptionConvertService.convert(e);
            Assert.assertEquals("argument_format_mismatch", result.getCode());
            System.out.println(result);
        }

    }
}
