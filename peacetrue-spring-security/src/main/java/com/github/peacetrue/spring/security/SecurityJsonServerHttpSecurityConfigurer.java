package com.github.peacetrue.spring.security;

import com.github.peacetrue.spring.web.server.ServerWebExchangeUtils;
import com.github.peacetrue.spring.web.server.WebFilterExchangeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author : xiayx
 * @since : 2020-06-26 17:23
 **/
@Slf4j
public class SecurityJsonServerHttpSecurityConfigurer implements ServerHttpSecurityConfigurer {

    @Autowired
    private SecurityJsonProperties properties;
    @Autowired
    private DispatcherHandler dispatcherHandler;
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Override
    public void configure(ServerHttpSecurity http) {
        log.info("配置 SecurityJson [{}]", properties);

        Set<String> ignoredUrls = new HashSet<>();
        if (properties.getIgnoredUrls() != null) ignoredUrls.addAll(properties.getIgnoredUrls());
        ignoredUrls.add(properties.getLoginFailureUrl());
        //退出之后认证就失效了，往退出成功转发时，会提示未认证，所以需要配置允许
        ignoredUrls.add(properties.getLogoutSuccessUrl());
        http
                .authorizeExchange(exchanges -> exchanges
                        .matchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()
                        .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .pathMatchers(ignoredUrls.toArray(new String[0])).permitAll()
//                        .anyExchange().authenticated()
                )
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource)
                .and()
//                .httpBasic(withDefaults())//for SBA
                .formLogin()
                .authenticationEntryPoint((exchange, e) -> dispatcherHandler.handle(ServerWebExchangeUtils.mutateRequestPath(exchange, properties.getUnauthorizedUrl())))
                .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, properties.getLoginUrl()))
                .authenticationFailureHandler((exchange, exception) -> {
                    ServerWebExchange webExchange = ServerWebExchangeUtils.mutateRequestPath(exchange.getExchange(), properties.getLoginFailureUrl());
                    webExchange.getAttributes().put(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
                    return exchange.getChain().filter(webExchange);
                })
                .authenticationSuccessHandler((webFilterExchange, authentication) -> WebFilterExchangeUtils.dispatch(webFilterExchange, properties.getLoginSuccessUrl()))
                .and()
                .logout()
                .logoutSuccessHandler((webFilterExchange, authentication) -> WebFilterExchangeUtils.dispatch(webFilterExchange, properties.getLogoutSuccessUrl()))
        ;

    }
}
