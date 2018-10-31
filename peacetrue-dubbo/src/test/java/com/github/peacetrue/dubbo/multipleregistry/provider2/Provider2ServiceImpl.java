package com.github.peacetrue.dubbo.multipleregistry.provider2;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author xiayx
 */
@Service
public class Provider2ServiceImpl implements Provider2Service {
    @Override
    public Object doSomething(Object object) {
        return object;
    }
}
