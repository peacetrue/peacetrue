package com.github.peacetrue.log;

import com.github.peacetrue.log.aspect.DefaultLogBuilder;
import com.github.peacetrue.log.aspect.LogAspect;
import com.github.peacetrue.log.aspect.LogBuilder;
import com.github.peacetrue.log.aspect.LogPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * the AutoConfiguration for Log
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class LogAutoConfiguration {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean(name = LogPoint.DEFAULT_LOG_BUILDER)
    @ConditionalOnMissingBean(name = LogPoint.DEFAULT_LOG_BUILDER)
    public LogBuilder logBuilder() {
        return new DefaultLogBuilder();
    }

    @Bean
    @ConditionalOnMissingBean(ExpressionParser.class)
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }
}
