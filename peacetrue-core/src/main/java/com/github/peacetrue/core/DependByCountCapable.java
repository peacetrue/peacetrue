package com.github.peacetrue.core;

/**
 * 可获取被依赖记录数
 *
 * @author xiayx
 * @see DependByCountAware
 */
public interface DependByCountCapable {

    /**
     * 获取被依赖记录数
     *
     * @return 被依赖记录数
     */
    Long getDependByCount();
}
