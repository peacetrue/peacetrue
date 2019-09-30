package com.github.peacetrue.uuid;

/**
 * 使用纳秒时间戳，15位
 *
 * @author xiayx
 */
public class NanoUUIDGenerator implements UUIDGenerator<Long> {

    public Long next() {
        return System.nanoTime();
    }

}
