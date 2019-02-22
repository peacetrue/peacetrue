package com.github.peacetrue.log.user;

import com.github.peacetrue.log.LogAutoConfiguration;
import com.github.peacetrue.log.service.LogServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
                UserServiceImpl.class,
                LogServiceImpl.class,
                CreatorIdProviderImpl.class,
                LogAutoConfiguration.class
        },
        properties = {
                "logging.level.root=info",
                "logging.level.com.github.peacetrue=trace",
                "spring.jpa.generate-ddl=true",
                "peacetrue.log.concreteClass=com.github.peacetrue.log.service.Log",
        }
)
@EntityScan(basePackages = "com.github.peacetrue.log")
@EnableAspectJAutoProxy
public class LogTest {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void add() throws Exception {
        userService.add(getUser());
    }

    private User getUser() {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setCreatedTime(new Date());
        return user;
    }
}
