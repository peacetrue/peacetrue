package com.github.peacetrue.operator.reactive;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : xiayx
 * @since : 2020-11-22 09:58
 **/
@Data
@ConfigurationProperties(prefix = "peacetrue.operator")
public class ReactiveOperatorProperties {

    private Pointcut pointcut = new Pointcut();

    @Data
    public static class Pointcut {
        /** 拦截切面表达式 */
        private String expression = "execution(* com.github.peacetrue..*ServiceImpl.*(*,..))";
    }
}
