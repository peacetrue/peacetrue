package com.github.peacetrue.printer;

import com.github.peacetrue.util.AssertUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * the tests for {@link MessageSourceClassPrinter}
 *
 * @author xiayx
 */
public class MessageSourceClassPrinterTest {

    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("result-builder");
        System.out.println(bundle.getBaseBundleName());
        System.out.println(bundle.getLocale());
    }

    public static class Configuration {
        @Bean
        public MessageSourceClassPrinter messageSourceClassPrinter() {
            MessageSourceClassPrinter classPrinter = new MessageSourceClassPrinter();
            classPrinter.setPrefix("Class");
            return classPrinter;
        }
    }

    @Profile("hasObject")
    public static class HasObjectConfiguration {
        @Bean
        public ResourceBundleMessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasenames("com.github.peacetrue.printer.messages-has-object");
            return messageSource;
        }
    }

    @Profile("noObject")
    public static class NoObjectConfiguration {
        @Bean
        public ResourceBundleMessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasenames("com.github.peacetrue.printer.messages-no-object");
            return messageSource;
        }
    }


    @ContextConfiguration(classes = {Configuration.class, HasObjectConfiguration.class, NoObjectConfiguration.class})
    public static class Abstract {
        @Autowired
        protected MessageSourceClassPrinter messageSourceClassPrinter;
    }

    @RunWith(SpringRunner.class)
    @ActiveProfiles("hasObject")
    public static class HasObject extends Abstract {
        @Test
        public void print() throws Exception {
            Assert.assertEquals("<未知类型>", messageSourceClassPrinter.print(MessageSourceClassPrinterTest.class));
            Assert.assertEquals("<未知类型>", messageSourceClassPrinter.print(Abstract.class));
        }
    }

    @RunWith(SpringRunner.class)
    @ActiveProfiles("noObject")
    public static class NoObject extends Abstract {
        @Test
        public void print() throws Exception {
            String number = "数值";
            Assert.assertEquals(number, messageSourceClassPrinter.print(Long.class));
            Assert.assertEquals(number, messageSourceClassPrinter.print(Integer.class));
            Assert.assertEquals(number, messageSourceClassPrinter.print(Float.class));
            String date = "日期";
            Assert.assertEquals(date, messageSourceClassPrinter.print(java.sql.Time.class));
            Assert.assertEquals(date, messageSourceClassPrinter.print(java.sql.Date.class));
            Assert.assertEquals(date, messageSourceClassPrinter.print(Date.class));
            AssertUtils.assertException(() -> messageSourceClassPrinter.print(MessageSourceClassPrinterTest.class));
        }
    }


}