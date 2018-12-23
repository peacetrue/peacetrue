package com.github.peacetrue.sign;

import com.github.peacetrue.core.DataCapable;

/**
 * 可被签名的
 *
 * @author xiayx
 */
public interface Signable<T> extends AppSecretCapable, DataCapable<T> {

}
