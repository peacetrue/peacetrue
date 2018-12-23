package com.github.peacetrue.sign;

import javax.annotation.Nullable;

/**
 * used to get secret
 *
 * @author xiayx
 */
public interface SecretProvider {

    /**
     * get app secret by app id
     *
     * @param id the app id
     * @return the app secret
     */
    @Nullable
    String getSecretById(String id);

}
