package com.github.peacetrue.operator;

import com.github.peacetrue.core.OperatorCapable;

/**
 * 获取当前操作者
 *
 * @author : xiayx
 * @since : 2020-06-21 19:54
 **/
@FunctionalInterface
public interface OperatorSupplier {

    /** 获取当前操作者 */
    OperatorCapable<?> getOperator();

}
