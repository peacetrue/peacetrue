package com.github.peacetrue.sign;

import javax.annotation.Nullable;

/**
 * used to get secret
 *
 * @author xiayx
 */
public interface SecretProvider {

    /**
     * get secret by id
     *
     * @param id the id
     * @return the secret
     */
    @Nullable
    String getSecretById(String id);

}
