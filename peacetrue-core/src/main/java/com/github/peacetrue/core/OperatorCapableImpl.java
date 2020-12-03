package com.github.peacetrue.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiayx
 */
@Data
public class OperatorCapableImpl<Id> implements OperatorCapable<Id>, OperatorAware<Id>, Serializable {

    private static final long serialVersionUID = 0L;

    private Id operatorId;
    private String operatorName;
}
