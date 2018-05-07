package com.github.peacetrue.result;

import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.MessageSourceResultBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;

/**
 * the tests for {@link MessageSourceResultBuilder}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
public class MessageSourceResultBuilderTest {

    @Autowired
    private MessageSourceResultBuilder resultBuilder;

    @Test
    public void build() throws Exception {
        String code = "argument_type_mismatched";
        Object[] arguments = {"id", "a", Long.class};
        String data = "id";
        DataResult<String> build = resultBuilder.build(code, arguments, data);
        assertEquals(code, build.getCode());
        String format = "参数\"id\"的值\"a\"无法被转换成\"数值\"类型";
        assertEquals(format, build.getMessage());
        assertEquals(data, build.getData());
    }
}