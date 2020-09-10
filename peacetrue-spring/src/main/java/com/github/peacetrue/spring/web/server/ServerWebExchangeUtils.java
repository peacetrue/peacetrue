package com.github.peacetrue.spring.web.server;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.UnaryOperator;

/**
 * utils for {@link ServerWebExchange}
 *
 * @author : xiayx
 * @since : 2020-06-26 16:00
 **/
public abstract class ServerWebExchangeUtils {

    protected ServerWebExchangeUtils() {
    }

    /** mutate request path */
    public static ServerWebExchange mutateRequestPath(ServerWebExchange exchange, String path) {
        return mutateRequest(exchange, builder -> builder.path(path));
    }

    /** mutate request */
    public static ServerWebExchange mutateRequest(ServerWebExchange exchange, UnaryOperator<ServerHttpRequest.Builder> operator) {
        return exchange.mutate().request(operator.apply(exchange.getRequest().mutate()).build()).build();
    }
}
