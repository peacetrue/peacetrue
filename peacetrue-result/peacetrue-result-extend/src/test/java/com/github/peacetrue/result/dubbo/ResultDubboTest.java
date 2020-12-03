package com.github.peacetrue.result.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.peacetrue.result.exception.ResultException;
import com.github.peacetrue.util.AssertUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultDubboTest {

    @Reference
    private ResultDubboService resultDubboService;

    @Test
    public void run() throws Exception {
        Throwable throwable = AssertUtils.assertException(() -> resultDubboService.throwException());
        Assertions.assertTrue(throwable instanceof ResultException);
    }
}
