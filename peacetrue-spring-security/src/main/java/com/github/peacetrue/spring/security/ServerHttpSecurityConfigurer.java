package com.github.peacetrue.spring.security;

import org.springframework.security.config.web.server.ServerHttpSecurity;

/**
 * used to configure {@link ServerHttpSecurity}
 *
 * @author : xiayx
 * @since : 2020-06-26 17:20
 **/
public interface ServerHttpSecurityConfigurer {

    /** configure ServerHttpSecurity */
    void configure(ServerHttpSecurity http);

}
