package com.github.peacetrue.dubbo.multipleregistry.provider1;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author xiayx
 */
@Service
public class Provider1ServiceImpl implements Provider1Service {
    @Override
    public Object doSomething(Object object) {
        return object;
    }
}
