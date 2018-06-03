package com.github.peacetrue.security;

import org.junit.Assert;
import org.junit.Test;

/**
 * tests for {@link MessageDigestUtils}
 *
 * @author xiayx
 */
public class MessageDigestUtilsTest {
    @Test
    public void encode() throws Exception {
        String encode = MessageDigestUtils.encode(MessageDigestUtils.getMD5(), "1");
        Assert.assertEquals("C4CA4238A0B923820DCC509A6F75849B", encode);
    }

}