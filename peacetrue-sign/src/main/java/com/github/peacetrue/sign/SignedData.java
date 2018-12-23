package com.github.peacetrue.sign;

import com.github.peacetrue.core.DataCapable;
import lombok.Data;

/**
 * 已签名的数据
 *
 * @param <T> the type of data
 * @author xiayx
 */
@Data
public class SignedData<T> implements AppIdCapable, DataCapable<T> {

    private String appId;
    private String nonce;
    private Long timestamp;
    private String sign;
    private T data;


}
