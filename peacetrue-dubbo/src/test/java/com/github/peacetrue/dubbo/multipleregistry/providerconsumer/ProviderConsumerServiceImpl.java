package com.github.peacetrue.dubbo.multipleregistry.providerconsumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.peacetrue.dubbo.multipleregistry.provider1.Provider1Service;

/**
 * @author xiayx
 */
@Service
public class ProviderConsumerServiceImpl implements ProviderConsumerService {

    @Reference(registry = "provider1")
    private Provider1Service provider1Service;

    @Override
    public Object doSomething(Object object) {
        return provider1Service.doSomething(object);
    }
}
