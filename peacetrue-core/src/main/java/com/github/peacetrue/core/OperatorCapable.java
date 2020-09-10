package com.github.peacetrue.core;


/**
 * 当前操作者
 *
 * @author xiayx
 */
public interface OperatorCapable<Id> {

    /** 获取当前操作者标志 */
    Id getOperatorId();

    /** 获取当前操作者名称 */
    String getOperatorName();
}
