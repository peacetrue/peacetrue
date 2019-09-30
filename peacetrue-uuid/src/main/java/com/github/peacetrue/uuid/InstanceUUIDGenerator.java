package com.github.peacetrue.uuid;

import java.util.function.BinaryOperator;

/**
 * 部署实例代理，在生成的唯一标识后附加上实例标识，避免实例间重复
 *
 * @author xiayx
 */
public class InstanceUUIDGenerator<T> implements UUIDGenerator<T> {

    private UUIDGenerator<T> uuidGenerator;
    private T instanceId;
    private BinaryOperator<T> binaryOperator;

    /**
     * 构建一个Long型的实例生成器
     *
     * @param uuidGenerator 被代理的生成器
     * @param instanceId    部署实例标识
     * @param instanceCount 部署实例总数
     * @return 实例生成器
     */
    public static InstanceUUIDGenerator<Long> longGenerator(UUIDGenerator<Long> uuidGenerator, Long instanceId, int instanceCount) {
        InstanceUUIDGenerator<Long> generator = new InstanceUUIDGenerator<>();
        generator.setUuidGenerator(uuidGenerator);
        generator.setInstanceId(instanceId);
        generator.setBinaryOperator((a, b) -> a * instanceCount + b);
        return generator;
    }

    /**
     * 构建一个String型的实例生成器
     *
     * @param uuidGenerator 被代理的生成器
     * @param instanceId    部署实例标识
     * @return 实例生成器
     */
    public static InstanceUUIDGenerator<String> stringGenerator(UUIDGenerator<String> uuidGenerator, String instanceId) {
        InstanceUUIDGenerator<String> generator = new InstanceUUIDGenerator<>();
        generator.setUuidGenerator(uuidGenerator);
        generator.setInstanceId(instanceId);
        generator.setBinaryOperator(String::concat);
        return generator;
    }

    @Override
    public T next() {
        return binaryOperator.apply(uuidGenerator.next(), instanceId);
    }

    public void setUuidGenerator(UUIDGenerator<T> uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }

    public void setInstanceId(T instanceId) {
        this.instanceId = instanceId;
    }

    public void setBinaryOperator(BinaryOperator<T> binaryOperator) {
        this.binaryOperator = binaryOperator;
    }
}
