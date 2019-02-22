package com.github.peacetrue.log.user;

import com.github.peacetrue.log.aspect.CreatorIdProvider;
import com.github.peacetrue.log.aspect.LogBuilder;

/**
 * @author xiayx
 */
public class CreatorIdProviderImpl implements CreatorIdProvider {
    @Override
    public Object get(LogBuilder.Context context) {
        return 1;
    }
}
