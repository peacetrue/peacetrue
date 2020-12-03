package com.github.peacetrue.result;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assertions.assertEquals;

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
                "spring.messages.basename=com/github/peacetrue/result/MessageSourceResultBuilder"
        }
)
public class MessageSourceResultBuilderTest {

    @Autowired
    private MessageSourceResultBuilder resultBuilder;
    @Autowired
    private MessageSource messageSource;

    @Test
    public void build() throws Exception {
        String code = "MethodArgumentTypeMismatchException";
        Object[] arguments = {"id", "a", Long.class};
        String data = "id";
        DataResult<String> build = resultBuilder.build(code, arguments, data);
        assertEquals(code, build.getCode());
        String format = "参数\"id\"的值\"a\"无法被转换成\"数值\"类型";
        assertEquals(format, build.getMessage());
        assertEquals(data, build.getData());
    }

    @Test
    public void success() throws Exception {
        Result success = resultBuilder.success();
        Assertions.assertEquals(ResultType.success.name(), success.getCode());
        String expected = messageSource.getMessage("Result." + success.getCode(), null, LocaleContextHolder.getLocale());
        Assertions.assertEquals(expected, success.getMessage());
    }

    @Test
    public void failure() throws Exception {
        Result failure = resultBuilder.failure();
        Assertions.assertEquals(ResultType.failure.name(), failure.getCode());
        String expected = messageSource.getMessage("Result." + failure.getCode(), null, LocaleContextHolder.getLocale());
        Assertions.assertEquals(expected, failure.getMessage());
    }

}
