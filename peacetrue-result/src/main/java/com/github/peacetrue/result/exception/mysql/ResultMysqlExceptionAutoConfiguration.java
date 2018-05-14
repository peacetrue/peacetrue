package com.github.peacetrue.result.exception.mysql;

import com.github.peacetrue.result.ResultAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * the AutoConfiguration for mysql exception convert
 *
 * @author xiayx
 */
@ConditionalOnClass(com.mysql.jdbc.Driver.class)
@AutoConfigureAfter(ResultAutoConfiguration.BuilderAutoConfiguration.class)
@EnableConfigurationProperties(ResultMysqlExceptionProperties.class)
@PropertySource("classpath:com/github/peacetrue/result/application.properties")
public class ResultMysqlExceptionAutoConfiguration {

    private ResultMysqlExceptionProperties properties;

    public ResultMysqlExceptionAutoConfiguration(ResultMysqlExceptionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MysqlExceptionConverter mysqlExceptionConverter() {
        MysqlExceptionConverter converter = new MysqlExceptionConverter();
        converter.setCodePrefix(properties.getCodePrefix());
        converter.setTemplates(properties.getTemplates());
        return converter;
    }
}
