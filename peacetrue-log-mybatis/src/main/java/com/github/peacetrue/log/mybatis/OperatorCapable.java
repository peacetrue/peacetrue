package com.github.peacetrue.log.mybatis;


/**
 * 当前操作者
 *
 * @author xiayx
 */
public interface OperatorCapable<Id> {

    Id getOperatorId();

    String getOperatorName();
}
