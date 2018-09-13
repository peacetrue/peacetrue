package com.github.peacetrue.sign;

/**
 * 签名生成器
 *
 * @author xiayx
 */
public interface SignGenerator {

    /** 生成签名 */
    String generate(String params, String secret);

}
