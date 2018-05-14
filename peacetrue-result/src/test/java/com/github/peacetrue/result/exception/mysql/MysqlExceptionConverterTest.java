package com.github.peacetrue.result.exception.mysql;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultAutoConfiguration;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;

/**
 * tests for {@link java.sql.SQLException}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DruidDataSourceAutoConfigure.class,
        HibernateJpaAutoConfiguration.class,
        ResultAutoConfiguration.BuilderAutoConfiguration.class,
        ResultExceptionAutoConfiguration.class,
        ResultMysqlExceptionAutoConfiguration.class
})
@EntityScan
public class MysqlExceptionConverterTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ExceptionConvertService exceptionConvertService;

    @Test
    @Transactional
    public void MySQLIntegrityConstraintViolationException() throws Exception {
        entityManager.persist(getUser());
        try {
            entityManager.persist(getUser());
        } catch (Exception e) {
            Result result = exceptionConvertService.convert(e, Locale.getDefault());
            Assert.assertEquals("mysql_1062", result.getCode());
        }
    }

    private User getUser() {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        return user;
    }
}
