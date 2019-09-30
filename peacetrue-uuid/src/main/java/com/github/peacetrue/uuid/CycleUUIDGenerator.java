package com.github.peacetrue.uuid;

/**
 * 可循环的，可以重置唯一标识重头开始
 *
 * @author xiayx
 */
public interface CycleUUIDGenerator<T> extends UUIDGenerator<T> {

    /**
     * 生成一个唯一标识
     *
     * @return 唯一标识
     * @throws UUIDOverflowException 唯一标识溢出时导致此异常
     */
    T next() throws UUIDOverflowException;

    /**
     * 获取当前的唯一标识
     *
     * @return 唯一标识
     */
    T get();

    /** 重置唯一标识 */
    void reset();

}
