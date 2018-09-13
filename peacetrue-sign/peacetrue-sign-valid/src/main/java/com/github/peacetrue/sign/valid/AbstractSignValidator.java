package com.github.peacetrue.sign.valid;

import com.github.peacetrue.sign.SignGenerator;
import com.github.peacetrue.sign.SignProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象签名验证器
 *
 * @author xiayx
 */
public abstract class AbstractSignValidator<T> implements SignValidator<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected SignProperties signProperties;
    protected SignGenerator signGenerator;

    @Autowired
    public void setSignProperties(SignProperties signProperties) {
        this.signProperties = signProperties;
    }

    @Autowired
    public void setSignGenerator(SignGenerator signGenerator) {
        this.signGenerator = signGenerator;
    }
}
