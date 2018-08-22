package com.github.peacetrue.result;

import com.github.peacetrue.printer.ClassPrinter;
import com.github.peacetrue.printer.MessageSourceClassPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * used to build result
 *
 * @author xiayx
 */
@EnableConfigurationProperties(ResultBuilderProperties.class)
public class ResultBuilderAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ResultBuilderProperties resultProperties;

    public ResultBuilderAutoConfiguration(ResultBuilderProperties resultProperties) {
        this.resultProperties = resultProperties;
    }

    @Bean
    public ResultBuilder resultBuilder() {
        return new CodeConvertResultBuilder();
    }

    @Bean
    public ResultBuilder messageSourceResultBuilder() {
        MessageSourceResultBuilder builder = new MessageSourceResultBuilder();
        builder.setPrefix(resultProperties.getCodePrefix());
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean(ClassPrinter.class)
    public ClassPrinter classPrinter() {
        MessageSourceClassPrinter printer = new MessageSourceClassPrinter();
        printer.setPrefix(resultProperties.getClassPrefix());
        return printer;
    }

    @Bean
    @ConditionalOnMissingBean(ResultCodeConverter.class)
    public ResultCodeConverter resultCodeResolver() {
        SimpleResultCodeResolver resolver = new SimpleResultCodeResolver();
        resolver.setCodes(resultProperties.getCodes());
        return resolver;
    }

    @Autowired
    public void addResultBuilderMessage(MessageSource messageSource) {
        //add default result builder messages config
        if (messageSource instanceof ResourceBundleMessageSource) {
            ((ResourceBundleMessageSource) messageSource).addBasenames("result-builder");
        } else {
            logger.warn("the default config 'result-builder.properties' not be added to MessageSource");
        }
    }


}
