package com.github.peacetrue.result;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * the tests for {@link MessageSourceResultBuilder}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                MessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class
        },
        properties = {
                "logging.level.root=info",
                "peacetrue.result.codes.success=0",
                "peacetrue.result.codes.failure=1",
                "spring.messages.basename=result-builder_zh_CN"
        }
)
public class ResultBuilderTest {

    @Autowired
    private ResultBuilder resultBuilder;
    @Autowired
    private MessageSource messageSource;

    @Test
    public void success() throws Exception {
        Result success = resultBuilder.success();
        Assert.assertEquals("0", success.getCode());
        String expected = messageSource.getMessage("Result.success", null, LocaleContextHolder.getLocale());
        Assert.assertEquals(expected, success.getMessage());
    }

    @Test
    public void error() throws Exception {
        Result error = resultBuilder.failure();
        Assert.assertEquals("1", error.getCode());
        String expected = messageSource.getMessage("Result.failure", null, LocaleContextHolder.getLocale());
        Assert.assertEquals(expected, error.getMessage());
    }

}