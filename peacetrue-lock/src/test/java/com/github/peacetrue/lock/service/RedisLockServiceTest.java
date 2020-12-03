package com.github.peacetrue.lock.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assertions.*;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockServiceTest {

    @Autowired
    private LockService lockService;

    @Test
    public void findAll() throws Exception {
        System.out.println(lockService.findAll());
    }

    @Test
    public void create() throws Exception {
        Lock hello = lockService.create("hello");
        System.out.println(hello);
    }

    @Test
    public void release() throws Exception {
    }

    @Test
    public void refresh() throws Exception {
    }

}
