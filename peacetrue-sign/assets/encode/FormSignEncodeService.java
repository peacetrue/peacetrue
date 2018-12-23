package com.github.peacetrue.sign.encode;

import com.github.peacetrue.sign.AppSecretCapable;
import com.github.peacetrue.sign.SignGenerator;
import com.github.peacetrue.sign.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Nullable;

/**
 * @author xiayx
 */
public class FormSignEncodeService implements UniteSignEncodeService<MultiValueMap<String, ?>> {

    private AppSecretCapable appSecret;
    private SignGenerator signGenerator;

    @Override
    public MultiValueMap<String, ?> sign(MultiValueMap<String, ?> data, @Nullable AppSecretCapable appSecret) {
        @SuppressWarnings("unchecked")
        MultiValueMap<String, Object> localData = new LinkedMultiValueMap<>((MultiValueMap) data);
        signProcess(localData, appSecret);
        return localData;
    }

    public void signProcess(MultiValueMap<String, Object> data, @Nullable AppSecretCapable appSecret) {
        if (appSecret == null) appSecret = this.appSecret;
        data.add("_sign", signGenerator.generate(SignUtils.concat(data), appSecret.getAppSecret()));
    }

    @Autowired
    public void setAppSecret(AppSecretCapable appSecret) {
        this.appSecret = appSecret;
    }
}
