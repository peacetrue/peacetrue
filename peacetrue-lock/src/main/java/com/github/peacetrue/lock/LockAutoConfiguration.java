package com.github.peacetrue.lock;

import com.github.peacetrue.lock.aspect.LockAspect;
import com.github.peacetrue.lock.service.LockService;
import com.github.peacetrue.lock.service.RedisLockService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(LockProperties.class)
@EnableAspectJAutoProxy
public class LockAutoConfiguration {

    private LockProperties properties;

    public LockAutoConfiguration(LockProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(LockService.class)
    public RedisLockService redisLockService(RedisConnectionFactory redisConnectionFactory) {
        RedisLockService redisLockService = new RedisLockService(redisConnectionFactory);
        redisLockService.setPrefix(properties.getPrefix());
        redisLockService.setExpiry(properties.getExpiry());
        return redisLockService;
    }

    @Bean
    @ConditionalOnMissingBean(ExpressionParser.class)
    public ExpressionParser expressionParser() {
        SpelParserConfiguration config = new SpelParserConfiguration(true,true);
        return new SpelExpressionParser(config);
    }

    @Bean
    public LockAspect lockAspect() {
        return new LockAspect();
    }
}
