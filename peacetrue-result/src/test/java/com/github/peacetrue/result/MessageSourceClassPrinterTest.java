package com.github.peacetrue.result;

import com.github.peacetrue.result.printer.MessageSourceClassPrinter;
import com.github.peacetrue.util.AssertUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;

/**
 * the tests for {@link MessageSourceClassPrinter}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
public class MessageSourceClassPrinterTest {

    @Autowired
    private MessageSourceClassPrinter classPrinter;

    @Test
    public void print() throws Exception {
        String number = "数值";
        Assert.assertEquals(number, classPrinter.print(Long.class));
        Assert.assertEquals(number, classPrinter.print(Integer.class));
        Assert.assertEquals(number, classPrinter.print(Float.class));
        String date = "日期";
        Assert.assertEquals(date, classPrinter.print(java.sql.Time.class));
        Assert.assertEquals(date, classPrinter.print(java.sql.Date.class));
        Assert.assertEquals(date, classPrinter.print(Date.class));
        AssertUtils.assertException(() -> classPrinter.print(MessageSourceClassPrinterTest.class));
    }

}