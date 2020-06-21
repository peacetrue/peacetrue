package com.github.peacetrue.operator;

import com.github.peacetrue.core.OperatorCapableImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 切面自动配置
 *
 * @author : xiayx
 * @since : 2020-06-21 14:56
 **/
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackageClasses = OperatorAutoConfiguration.class)
@ImportResource("classpath*:peacetrue-aop.xml")
public class OperatorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(OperatorSupplier.class)
    public OperatorSupplier operatorSupplier() {
        return () -> {
            OperatorCapableImpl<Long> operator = new OperatorCapableImpl<>();
            operator.setOperatorId(0L);
            operator.setOperatorName("system");
            return operator;
        };
    }

}
