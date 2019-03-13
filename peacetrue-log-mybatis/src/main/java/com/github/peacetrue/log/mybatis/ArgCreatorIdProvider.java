package com.github.peacetrue.log.mybatis;

import com.github.peacetrue.log.aspect.AfterMethodBasedEvaluationContext;
import com.github.peacetrue.log.aspect.CreatorIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * @author xiayx
 */
public class ArgCreatorIdProvider<Id> implements CreatorIdProvider<Id> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public Id getCreatorId(AfterMethodBasedEvaluationContext context) {
        Id id = Arrays.stream(context.getArguments())
                .filter(arg -> arg instanceof OperatorCapable).findFirst()
                .map(arg -> ((OperatorCapable<Id>) arg).getOperatorId()).orElse(null);
        logger.debug("从参数中获取当前用户: {}", id);
        return id;
    }

}
