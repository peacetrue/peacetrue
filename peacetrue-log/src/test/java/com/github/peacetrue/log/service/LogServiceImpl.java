package com.github.peacetrue.log.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author xiayx
 */
@Service
public class LogServiceImpl implements LogService<Log> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Log log) {
        logger.info("新增日志: {}", log);
        entityManager.persist(log);
    }


}
