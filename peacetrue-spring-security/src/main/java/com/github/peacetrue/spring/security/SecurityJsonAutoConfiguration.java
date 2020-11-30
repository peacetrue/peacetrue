package com.github.peacetrue.spring.security;

import com.github.peacetrue.spring.web.cors.CorsConfigurationUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import java.util.List;

/**
 * @author : xiayx
 * @since : 2020-06-26 17:05
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecurityJsonProperties.class)
public class SecurityJsonAutoConfiguration {

    @Bean
    public SecurityJsonAdapterController securityJsonAdapterController() {
        return new SecurityJsonAdapterController();
    }

    @Bean
    @ConditionalOnMissingBean(CorsConfigurationSource.class)
    public CorsConfigurationSource corsConfigurationSource() {
        return exchange -> CorsConfigurationUtils.supportAll();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    @ConditionalOnMissingBean(name = "securityJsonServerHttpSecurityConfigurer")
    public SecurityJsonServerHttpSecurityConfigurer securityJsonServerHttpSecurityConfigurer() {
        return new SecurityJsonServerHttpSecurityConfigurer();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    @ConditionalOnMissingBean(name = "anyExchangeAuthenticatedServerHttpSecurityConfigurer")
    public ServerHttpSecurityConfigurer anyExchangeAuthenticatedServerHttpSecurityConfigurer() {
        return http -> http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                );
    }

    @EnableWebFluxSecurity
    @ConditionalOnMissingBean(SecurityWebFilterChain.class)
    public static class WebFluxSecurityConfig {
        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                             List<ServerHttpSecurityConfigurer> serverHttpSecurityConfigurers) {
            serverHttpSecurityConfigurers.forEach(configurer -> configurer.configure(http));
            return http.build();
        }
    }
}
