package com.github.peacetrue.lock.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LockClientServiceImplTest {

    @Autowired
    private LockClientService lockClientService;

    @Test
    public void doSomethingSuccess() throws Exception {
        //同步执行，每个方法都能取得锁，不会出现异常
        for (int i = 0; i < 2; i++) {
            DTO dto = new DTO();
            dto.setUserId("wang");
            lockClientService.doSomething(dto);
        }
    }

    @Test
    public void doSomethingFailure() throws Exception {
        //并发执行，只有一个方法能取得锁
        DTO dto = new DTO();
        dto.setUserId("wang");

        int totalCount = 5;
        AtomicInteger
                lockSuccessCount = new AtomicInteger(0),
                lockFailureCount = new AtomicInteger(0);
        CountDownLatch downLatch = new CountDownLatch(totalCount);
        ExecutorService pool = Executors.newFixedThreadPool(totalCount);
        for (int i = 0; i < totalCount; i++) {
            pool.execute(() -> {
                try {
                    lockClientService.doSomething(dto);
                    lockSuccessCount.incrementAndGet();
                } catch (Throwable e) {
                    lockFailureCount.incrementAndGet();
                } finally {
                    downLatch.countDown();
                }
            });
        }
        downLatch.await();
        Assert.assertEquals(1, lockSuccessCount.get());
        Assert.assertEquals(totalCount - 1, lockFailureCount.get());
    }
}
