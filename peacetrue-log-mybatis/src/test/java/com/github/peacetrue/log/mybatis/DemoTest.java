package com.github.peacetrue.log.mybatis;

import com.github.peacetrue.log.aspect.LogInfo;
import com.github.peacetrue.log.aspect.LogPoint;
import com.github.peacetrue.log.aspect.Module;
import com.github.peacetrue.log.aspect.Operate;

/**
 * @author xiayx
 */
public class DemoTest {

    @LogPoint
    @Operate(module = @Module(code = "go"), code = "go")
    @LogInfo(description = "go")
    public void go(OperatorCapable operator) {
        System.out.println("GO");
    }
}
