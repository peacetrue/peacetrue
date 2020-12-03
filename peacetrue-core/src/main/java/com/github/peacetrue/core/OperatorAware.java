package com.github.peacetrue.core;


/**
 * 当前操作者
 *
 * @author xiayx
 */
public interface OperatorAware<Id> {

    /** 设置当前操作者标志 */
    void setOperatorId(Id operatorId);

    /** 设置当前操作者名称 */
    void setOperatorName(String operatorName);
}
