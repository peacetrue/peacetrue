package com.github.peacetrue.sign.validator;

import com.github.peacetrue.sign.AppBean;
import com.github.peacetrue.sign.SignProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

import static com.github.peacetrue.sign.validator.SignValidatorProperties.PREFIX;

/**
 * 签名验证属性
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = PREFIX)
public class SignValidatorProperties extends SignProperties {

    /** 服务端应用配置 */
    @NestedConfigurationProperty
    private List<AppBean> server = new ArrayList<>();

    public List<AppBean> getServer() {
        return server;
    }

    public void setServer(List<AppBean> server) {
        this.server = server;
    }
}
