package com.github.peacetrue.core;

import java.io.Serializable;

/**
 * @author xiayx
 */
public class OperatorCapableImpl<Id> implements OperatorCapable<Id>, Serializable {

    private static final long serialVersionUID = 0L;

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
