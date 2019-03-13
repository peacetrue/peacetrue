package com.github.peacetrue.log.mybatis;

import java.util.List;

/**
 * @author xiayx
 */
public interface LogService extends com.github.peacetrue.log.service.LogService<Log> {

    /** 查询指定记录的操作日志 */
    List<LogVO> query(String moduleCode, String recordId);

}
