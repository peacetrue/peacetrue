package com.github.peacetrue.log.user;

import com.github.peacetrue.log.aspect.LogInfo;
import com.github.peacetrue.log.aspect.LogPoint;
import com.github.peacetrue.log.aspect.Module;
import com.github.peacetrue.log.aspect.Operate;
import lombok.Getter;
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
@Module(code = "user")
@Getter
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Operate(code = "add")
    @LogInfo(recordId = "#returning", description = "新增用户#{#p0.name}")
    @Transactional
    //tag::class[]
    @LogPoint
    public Long add(User user) {
        logger.info("新增用户: {}", user);
        entityManager.persist(user);
        return user.getId();
    }
    //end::class[]
}
