package com.github.peacetrue.result;

import com.github.peacetrue.printer.ClassPrinter;
import com.github.peacetrue.printer.MessageSourceClassPrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * the tests for {@link MessageSourceResultBuilder}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MessageSourceResultBuilderTest.Configuration.class)
public class MessageSourceResultBuilderTest {

    public static class Configuration {
        @Bean
        public MessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasenames("com.github.peacetrue.result.MessageSourceResultBuilder");
            return messageSource;
        }

        @Bean
        public ResultCodeResolver resultCodeResolver() {
            return new SimpleResultCodeResolver();
        }

        @Bean
        public ClassPrinter classPrinter() {
            return new MessageSourceClassPrinter();
        }

        @Bean
        public ResultBuilder resultBuilder() {
            return new MessageSourceResultBuilder();
        }
    }

    @Autowired
    private MessageSourceResultBuilder messageSourceResultBuilder;

    @Test
    public void build() throws Exception {
        String code = "MethodArgumentTypeMismatchException";
        Object[] arguments = {"id", "a", Long.class};
        String data = "id";
        DataResult<String> build = messageSourceResultBuilder.build(code, arguments, data);
        assertEquals(code, build.getCode());
        String format = "参数\"id\"的值\"a\"无法被转换成\"数值\"类型";
        assertEquals(format, build.getMessage());
        assertEquals(data, build.getData());
    }
}