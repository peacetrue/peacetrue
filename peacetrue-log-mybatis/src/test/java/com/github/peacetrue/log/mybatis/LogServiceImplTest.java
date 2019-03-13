package com.github.peacetrue.log.mybatis;

import com.github.peacetrue.log.LogAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class,
        MybatisLogAutoConfiguration.class,
        LogAutoConfiguration.class,
        DemoTest.class
}, properties = {
        "peacetrue.log.async=false",
        "spring.datasource.initialize=true",
        "spring.jpa.generate-ddl=true",
//        "logging.level.root=trace",
}
)
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LogServiceImplTest {

    @Autowired
    private DemoTest demoTest;

    @Test
    public void add() throws Exception {
        demoTest.go(new OperatorCapable() {
            @Override
            public String getOperatorId() {
                return "1";
            }

            @Override
            public String getOperatorName() {
                return "1";
            }
        });
    }

    @Test
    public void query() throws Exception {

    }

}