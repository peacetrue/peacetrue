package com.github.peacetrue.sign.server;

import com.github.peacetrue.sign.SignUtils;
import com.github.peacetrue.sign.Signed;
import com.github.peacetrue.spring.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * sign data with json
 *
 * @author xiayx
 */
public class JsonServerSignService implements ServerSignService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String signProperty;

    @Override
    public boolean checkSign(Object signedValue, String secret) {
        logger.info("check sign");
        Map<String, Object> params = BeanUtils.map(signedValue);
        Object clientSign = params.remove(signProperty);
        logger.debug("the client sign is {}", clientSign);
        String serverSign = SignUtils.generateSign(params, secret);
        logger.debug("use the params: {} and secret: {} generate sign: {}", params, secret, serverSign);
        return clientSign.equals(serverSign);
    }

    @Override
    public Object extractData(Object signedValue) {
        logger.info("extract data from signed value");
        return ((Signed) signedValue).getData();
    }

    public void setSignProperty(String signProperty) {
        this.signProperty = signProperty;
    }
}
