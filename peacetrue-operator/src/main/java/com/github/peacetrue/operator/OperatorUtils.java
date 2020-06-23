package com.github.peacetrue.operator;

import com.github.peacetrue.core.OperatorCapable;
import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作者工具类
 *
 * @author xiayx
 */
@Slf4j
public abstract class OperatorUtils {

    public static final OperatorCapableImpl<String> OPERATOR = new OperatorCapableImpl<>();

    static {
        OPERATOR.setOperatorId("system");
        OPERATOR.setOperatorName("系统");
    }

    @SuppressWarnings("unchecked")
    public static void setOperators(OperatorCapableImpl<?> operator, OperatorCapable<?> defaultOperator) {
        setOperator((OperatorCapableImpl<Object>) operator, (OperatorCapableImpl<Object>) defaultOperator);
    }

    public static <T> void setOperator(OperatorCapableImpl<T> operator, OperatorCapable<T> defaultOperator) {
        log.debug("设置当前操作者:[{}]", defaultOperator);
        if (operator.getOperatorId() == null) operator.setOperatorId(defaultOperator.getOperatorId());
        if (operator.getOperatorName() == null) operator.setOperatorName(defaultOperator.getOperatorName());
    }

}
