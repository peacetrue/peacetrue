package com.github.peacetrue.uuid;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 逐步递增。从一个起点开始，每次逐步递增
 *
 * @author xiayx
 */
public class IncreaseUUIDGenerator implements CycleUUIDGenerator<Long> {

    /** 起始值 */
    private long originValue = 0L;
    /** 递增量 */
    private int incrementValue = 1;
    /** 最大值 */
    private long maxValue = Long.MAX_VALUE;
    /** 递增器 */
    private AtomicLong atomic = new AtomicLong(originValue);

    @Override
    public Long next() {
        if (atomic.get() == maxValue) {
            throw new UUIDOverflowException(maxValue);
        }
        return atomic.incrementAndGet();
    }

    @Override
    public Long get() {
        return atomic.get();
    }

    @Override
    public void reset() {
        atomic.set(originValue);
    }

    public void setOriginValue(long originValue) {
        this.originValue = originValue;
    }

    public long getOriginValue() {
        return originValue;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }
}
