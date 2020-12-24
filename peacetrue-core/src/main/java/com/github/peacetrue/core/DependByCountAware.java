package com.github.peacetrue.core;

/**
 * 可设置被依赖记录数
 *
 * @author xiayx
 * @see CodeAware
 */
public interface DependByCountAware {

    /**
     * 设置被依赖记录数
     */
    void setDependByCount(Long dependByCount);
}
