package com.github.peacetrue.sign.encode;

import com.github.peacetrue.sign.AppSecretCapable;

/**
 * sign data with json
 *
 * @author xiayx
 */
public class RequestBodySignEncodeService  {

    private AppSecretCapable appSecret;

//    @Override
//    public SignedBean<String> sign(Object data, @Nullable AppSecretCapable appSecret) {
//        if (appSecret == null) appSecret = this.appSecret;
//        return super.sign(data, appSecret);
//    }

    public void setAppSecret(AppSecretCapable appSecret) {
        this.appSecret = appSecret;
    }
}
