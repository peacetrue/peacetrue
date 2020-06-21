package com.github.peacetrue.operator;

import com.github.peacetrue.core.OperatorCapable;
import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作者通知。
 * 设置操作者为当前用户。
 *
 * @author xiayx
 */
@Slf4j
public class OperatorAdvisor {

    @Autowired
    private OperatorSupplier operatorSupplier;

    @SuppressWarnings("unchecked")
    public void setOperator(OperatorCapableImpl<?> operator) {
        log.debug("设置当前操作者:[{}]", 0L);
        OperatorCapable<?> defaultOperator = operatorSupplier.getOperator();
        if (operator.getOperatorId() == null)
            ((OperatorCapableImpl<Object>) operator).setOperatorId(defaultOperator.getOperatorId());
        if (operator.getOperatorName() == null) operator.setOperatorName(defaultOperator.getOperatorName());
    }

}
