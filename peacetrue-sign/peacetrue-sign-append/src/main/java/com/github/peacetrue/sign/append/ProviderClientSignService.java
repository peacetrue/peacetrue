//package com.github.peacetrue.sign.encode;
//
//import com.github.peacetrue.sign.SecretProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Objects;
//
///**
// * sign data for provider
// *
// * @author xiayx
// */
//public class ProviderClientSignService extends AbstractSignEncodeService {
//
//    private SecretProvider secretProvider;
//
//    @Override
//    public Object sign(Object data, String appId, String appSecret) {
//        logger.info("sign the data");
//        Objects.requireNonNull(appId);
//        if (appSecret == null) appSecret = secretProvider.getSecretById(appId);
//        return super.sign(data, appId, appSecret);
//    }
//
//    @Autowired
//    public void setSecretProvider(SecretProvider secretProvider) {
//        this.secretProvider = secretProvider;
//    }
//}
