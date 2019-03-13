package com.github.peacetrue.log.mybatis;

import com.github.peacetrue.spring.util.BeanUtils;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.peacetrue.log.mybatis.LogDynamicSqlSupport.log;

/**
 * @author xiayx
 */
public class LogServiceImpl implements LogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogMapper logMapper;

    public void add(Log log) {
        logger.info("添加日志: {}", log);
        logMapper.insert(log);
    }

    @Override
    public List<LogVO> query(String moduleCode, String recordId) {
        logger.info("查询指定记录'{}-{}'的操作日志", moduleCode, recordId);
        List<Log> logs = logMapper.selectByExample()
                .where(log.moduleCode, SqlBuilder.isEqualTo(moduleCode))
                .and(log.recordId, SqlBuilder.isEqualTo(recordId))
                .orderBy(log.createdTime)
                .build().execute();
        return logs.stream().map(vo -> BeanUtils.toSubclass(vo, LogVO.class)).collect(Collectors.toList());
    }

}
