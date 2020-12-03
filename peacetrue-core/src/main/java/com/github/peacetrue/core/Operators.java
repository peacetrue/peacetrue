package com.github.peacetrue.core;

/**
 * @author : xiayx
 * @since : 2020-11-30 16:08
 **/
public abstract class Operators {

    protected Operators() {
    }

    public static <S extends OperatorCapable<Id>, T extends OperatorAware<Id>, Id> T setOperator(S source, T target) {
        target.setOperatorId(source.getOperatorId());
        target.setOperatorName(source.getOperatorName());
        return target;
    }
}
