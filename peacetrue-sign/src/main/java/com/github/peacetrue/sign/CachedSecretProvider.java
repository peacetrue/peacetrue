package com.github.peacetrue.sign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * get from cached map
 *
 * @author xiayx
 */
public class CachedSecretProvider implements SecretProvider {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, String> secrets = new HashMap<>();


    public CachedSecretProvider() {
    }

    public CachedSecretProvider(Map<String, String> secrets) {
        this.secrets = secrets;
    }

    public CachedSecretProvider(List<? extends AppSecret> appSecrets) {
        this.secrets = appSecrets.stream().collect(Collectors.toMap(AppId::getAppId, AppSecret::getAppSecret));
    }

    @Override
    public String getSecretById(String id) {
        logger.info("get secret for id: {}", id);
        return secrets.get(id);
    }

    public Map<String, String> getSecrets() {
        return secrets;
    }

    public void setSecrets(Map<String, String> secrets) {
        this.secrets = Objects.requireNonNull(secrets);
    }
}