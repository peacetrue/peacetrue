package com.github.peacetrue.core;

/**
 * @author xiayx
 */
public class OperatorCapableImpl<Id> implements OperatorCapable<Id> {

    private Id operatorId;
    private String operatorName;

    public Id getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Id operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
