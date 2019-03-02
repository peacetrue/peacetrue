package com.github.peacetrue.log.user;

import com.github.peacetrue.log.aspect.AfterMethodBasedEvaluationContext;
import com.github.peacetrue.log.aspect.CreatorIdProvider;

/**
 * @author xiayx
 */
public class CreatorIdProviderImpl implements CreatorIdProvider {
    @Override
    public Object getCreatorId(AfterMethodBasedEvaluationContext context) {
        return 1;
    }
}
