package com.github.peacetrue.log.service;

/**
 * 日志服务
 *
 * @param <Log> the concrete class type of {@link AbstractLog}
 * @author xiayx
 */
public interface LogService<Log extends AbstractLog> {

    /**
     * 添加日志
     *
     * @param log 日志信息
     */
    void add(Log log);

}
