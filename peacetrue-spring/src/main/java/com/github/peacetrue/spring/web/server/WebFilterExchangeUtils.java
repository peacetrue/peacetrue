package com.github.peacetrue.spring.web.server;

import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

/**
 * utils for {@link WebFilterExchange}
 *
 * @author : xiayx
 * @since : 2020-06-26 16:11
 **/
public abstract class WebFilterExchangeUtils {

    protected WebFilterExchangeUtils() {
    }

    /** dispatch by filter */
    public static Mono<Void> dispatch(WebFilterExchange exchange, String path) {
        return exchange.getChain().filter(ServerWebExchangeUtils.mutateRequestPath(exchange.getExchange(), path));
    }
}
