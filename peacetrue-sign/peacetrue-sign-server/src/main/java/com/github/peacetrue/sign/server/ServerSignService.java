package com.github.peacetrue.sign.server;

/**
 * a service for server check sign
 *
 * @author xiayx
 */
public interface ServerSignService {

    /**
     * check the signed value
     *
     * @param signedValue the signed value
     * @param secret      the secret
     * @return the original value
     */
    boolean checkSign(Object signedValue, String secret);

    /**
     * extract data from signed value
     *
     * @param signedValue the signed value
     * @return the data
     */
    Object extractData(Object signedValue);

}
