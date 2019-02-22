package com.github.peacetrue.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author xiayx
 */
@Service
public class LogServiceImpl implements LogService<Log> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Log log) {
        entityManager.persist(log);
    }


}
