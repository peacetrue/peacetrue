package com.github.peacetrue.log.mybatis;

import com.github.peacetrue.log.aspect.CreatorIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiayx
 */
@Configuration
@MapperScan(basePackageClasses = MybatisLogAutoConfiguration.class)
@PropertySource("classpath:peacetrue-log-mybatis.properties")
public class MybatisLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LogService.class)
    public LogService logService() {
        return new LogServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(CreatorIdProvider.class)
    public CreatorIdProvider<String> creatorIdProvider() {
        return new ArgCreatorIdProvider();
    }

}
