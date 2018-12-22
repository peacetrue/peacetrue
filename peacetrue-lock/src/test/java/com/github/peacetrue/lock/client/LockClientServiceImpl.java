package com.github.peacetrue.lock.client;

import com.github.peacetrue.lock.aspect.LockPoint;
import org.springframework.stereotype.Service;

/**
 * @author xiayx
 */
@Service
public class LockClientServiceImpl implements LockClientService {

    @Override
    @LockPoint(key = "client:#{[0].userId}")
    public void doSomething(DTO dto) {
        System.out.println("do something for " + dto);
    }
}
