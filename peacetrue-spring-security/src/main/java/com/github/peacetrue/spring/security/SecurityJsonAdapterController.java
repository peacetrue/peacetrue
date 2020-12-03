package com.github.peacetrue.spring.security;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : xiayx
 * @since : 2020-03-18 13:19
 **/
@Slf4j
@RestController
public class SecurityJsonAdapterController {

    @RequestMapping("${peacetrue.spring.security.unauthorized-url:/unauthorized}")
    public Mono<ResponseEntity<Result>> unauthorized(ServerWebExchange exchange) {
        log.debug("请求[{}]尚未认证", exchange.getRequest().getURI());
        return Mono.just(new ResponseEntity<>(
                new ResultImpl("unauthorized", "用户尚未登陆或会话已过期"),
                HttpStatus.UNAUTHORIZED
        ));
    }

    @RequestMapping("${peacetrue.spring.security.login-success-url:/login/success}")
    public Mono<Object> loginSuccess(ServerWebExchange exchange) {
        log.debug("请求[{}]登陆成功", exchange.getRequest().getURI());
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getPrincipal());
    }

    @RequestMapping("${peacetrue.spring.security.login-failure-url:/login/failure}")
    public Mono<ResponseEntity<Result>> loginFailure(ServerWebExchange exchange) {
        AuthenticationException exception = exchange.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        log.warn("请求[{}]登陆失败", exchange.getRequest().getURI(), exception);
        //TODO exception converter
        return Mono.just(new ResponseEntity<>(
                new ResultImpl("login.failure", "账号或密码错误"),
                HttpStatus.UNAUTHORIZED
        ));
    }

    @RequestMapping("${peacetrue.spring.security.logout-success-url:/logout/success}")
    public Mono<Result> logoutSuccess(ServerWebExchange exchange) {
        log.debug("请求[{}]退出成功", exchange.getRequest().getURI());
        return Mono.just(new ResultImpl("success", "退出成功"));
    }

}
