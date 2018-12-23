package com.github.peacetrue.sign.decode;

import com.github.peacetrue.sign.SignGenerator;
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
    protected String signProperty;
    protected SignGenerator signGenerator;

    public void setSignProperty(String signProperty) {
        this.signProperty = signProperty;
    }

    @Autowired
    public void setSignGenerator(SignGenerator signGenerator) {
        this.signGenerator = signGenerator;
    }
}
