package com.github.peacetrue.uuid;

/**
 * 唯一标识生成器
 *
 * @author xiayx
 */
public interface UUIDGenerator<T> {

    /**
     * 生成一个唯一标识
     *
     * @return 唯一标识
     */
    T next();

}
