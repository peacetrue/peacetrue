package com.github.peacetrue.sign.client;

import org.springframework.web.client.RestTemplate;

/**
 * a factory for {@link RestTemplate}
 *
 * @author xiayx
 */
public interface RestTemplateFactory {

    /**
     * get RestTemplate
     *
     * @param appId     the appId
     * @param appSecret the appSecret
     * @return the RestTemplate
     */
    RestTemplate getRestTemplate(String appId, String appSecret);
}
