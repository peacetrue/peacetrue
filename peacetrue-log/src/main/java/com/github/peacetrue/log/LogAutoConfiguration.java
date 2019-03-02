package com.github.peacetrue.log;

import com.github.peacetrue.log.aspect.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * the AutoConfiguration for Log
 *
 * @author xiayx
 */
@Configuration
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(LogProperties.class)
@EnableAsync
@EnableAspectJAutoProxy
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "async", havingValue = "false")
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
        SpelParserConfiguration configuration = new SpelParserConfiguration(
                SpelCompilerMode.IMMEDIATE, null, true, true, Integer.MAX_VALUE);
        return new SpelExpressionParser(configuration);
    }

    @Configuration
    @ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "async", havingValue = "true", matchIfMissing = true)
    public static class AsyncConfiguration {

        @Bean
        public LogAspect asyncLogAspect() {
            return new AsyncLogAspect();
        }

        /** 日志任务执行器 */
        @Bean
        @ConditionalOnMissingBean(name = "logTaskExecutor")
        public Executor logTaskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(20);
            executor.setQueueCapacity(200);
            executor.setKeepAliveSeconds(60);
            executor.setThreadNamePrefix("logTaskExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }

    }


}
