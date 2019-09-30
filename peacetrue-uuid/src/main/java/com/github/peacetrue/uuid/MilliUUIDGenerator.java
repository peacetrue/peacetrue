package com.github.peacetrue.uuid;

/**
 * 使用微秒时间戳，13位
 *
 * @author xiayx
 */
public class MilliUUIDGenerator implements UUIDGenerator<Long> {

    @Override
    public Long next() {
        return System.currentTimeMillis();
    }
}
