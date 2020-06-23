package com.github.peacetrue.operator.reactive;

import com.github.peacetrue.core.OperatorCapable;
import reactor.core.publisher.Mono;

/**
 * 获取当前操作者
 *
 * @author : xiayx
 * @since : 2020-06-21 19:54
 **/
@FunctionalInterface
public interface ReactiveOperatorSupplier {

    /** 获取当前操作者 */
    Mono<OperatorCapable<?>> getOperator();

}
