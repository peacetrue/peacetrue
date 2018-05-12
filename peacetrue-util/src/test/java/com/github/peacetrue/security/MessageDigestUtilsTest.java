package com.github.peacetrue.security;

import org.junit.Test;

/**
 * tests for {@link MessageDigestUtils}
 *
 * @author xiayx
 */
public class MessageDigestUtilsTest {
    @Test
    public void encode() throws Exception {
        String encode = MessageDigestUtils.encode(MessageDigestUtils.getMD5(), "zhongjiao123");
        System.out.println(encode);
    }

}