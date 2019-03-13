package com.github.peacetrue.log.mybatis;

import lombok.Data;

/**
 * @author xiayx
 */
@Data
public class OperatorCapableImpl<Id> implements OperatorCapable<Id> {
    private Id operatorId;
    private String operatorName;
}
