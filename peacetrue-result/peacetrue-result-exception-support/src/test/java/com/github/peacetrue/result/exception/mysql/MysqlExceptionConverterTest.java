package com.github.peacetrue.result.exception.mysql;

import com.github.peacetrue.result.FailureType;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 * tests for {@link java.sql.SQLException}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                MessageSourceAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                ResultMysqlExceptionAutoConfiguration.class
        },
        properties = {
                "logging.level.root=info",
                "spring.jpa.generate-ddl=true",
                "spring.datasource.url=jdbc:mysql://localhost:3306/testdb?autoreconnect=true&useUnicode=true&characterEncoding=utf-8",
                "spring.datasource.username=root",
                "spring.datasource.password=root",
        }
)
@EntityScan
public class MysqlExceptionConverterTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ExceptionConvertService exceptionConvertService;

    @Test
    @Transactional
    public void MySQLIntegrityConstraintViolationException() throws Exception {
        try {
            entityManager.persist(getUser());
            entityManager.persist(getUser());
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof PersistenceException);
            Result result = exceptionConvertService.convert(e);
            System.out.println(result);
            Assert.assertEquals(FailureType.server_error.name(), result.getCode());
        }
    }

    private User getUser() {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        return user;
    }
}
