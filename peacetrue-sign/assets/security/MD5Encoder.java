package com.github.peacetrue.spring.security;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * md5加密
 *
 * @author xiayx
 */
public class MD5Encoder implements Encoder {

    public final static MD5Encoder DEFAULT = new MD5Encoder();

    private MD5Encoder() { }

    @Override
    public String encode(String string) {
        MessageDigest md5 = MessageDigestUtils.getMD5();
        byte[] digest = md5.digest(string.getBytes());
        return Base64.getEncoder().encodeToString(digest);
    }

}
