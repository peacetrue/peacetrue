package com.github.peacetrue.sign.appender;

import com.github.peacetrue.sign.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * 表单参数签名服务
 *
 * @author xiayx
 * @see FormSignHttpMessageConverter
 * @see RawSignService
 */
public class FormSignService implements SignService<MultiValueMap<String, ?>, MultiValueMap<String, ?>> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private AppSecretCapable appSecret;
    private SignGenerator signGenerator;
    private SignProperties signProperties;

    @Override
    public MultiValueMap<String, ?> sign(MultiValueMap<String, ?> params) {
        Object appId = params.getFirst(signProperties.getAppIdParamName());
        Object appSecret = params.getFirst(signProperties.getAppSecretParamName());
        AppSecretCapable appSecretCapable = appId == null || appSecret == null
                ? null : new AppBean(appId.toString(), appSecret.toString());
        return sign(params, appSecretCapable);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MultiValueMap<String, ?> sign(MultiValueMap<String, ?> params, @Nullable AppSecretCapable appSecret) {
        MultiValueMap<String, Object> localParams
//                = (MultiValueMap<String, Object>) params;
                = new LinkedMultiValueMap<>((MultiValueMap) params);
        append(localParams, appSecret);
        logger.debug("签名后的参数: {}", localParams);
        return localParams;
    }

    public void append(MultiValueMap<String, Object> params, @Nullable AppSecretCapable appSecret) {
        logger.debug("签名前的参数: {}", params);

        if (appSecret == null) appSecret = this.appSecret;

        if (!params.containsKey(signProperties.getAppIdParamName()))
            params.add(signProperties.getAppIdParamName(), appSecret.getAppId());

        if (params.containsKey(signProperties.getAppSecretParamName()))
            params.remove(signProperties.getAppSecretParamName());

        params.add(signProperties.getTimestampParamName(), String.valueOf(System.currentTimeMillis()));
        params.add(signProperties.getNonceParamName(), UUID.randomUUID().toString());
        //TODO 是否需要签名所有参数，上传时的流如何处理？
        params.add(signProperties.getSignParamName(),
                signGenerator.generate(SignUtils.concat(params), appSecret.getAppSecret()));
    }

    @Autowired
    public void setAppSecret(AppSecretCapable appSecret) {
        this.appSecret = appSecret;
    }

    @Autowired
    public void setSignProperties(SignProperties signProperties) {
        this.signProperties = signProperties;
    }

    @Autowired
    public void setSignGenerator(SignGenerator signGenerator) {
        this.signGenerator = signGenerator;
    }
}
