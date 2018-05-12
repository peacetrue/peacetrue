package com.github.peacetrue.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * md5加密
 *
 * @author xiayx
 */
public class MD5Encoder implements Encoder {

    public final static MD5Encoder DEFAULT = new MD5Encoder();

    private MD5Encoder() {
    }

    @Override
    public String encode(String string) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(string.getBytes());
            return Base64.getEncoder().encodeToString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new EncodeException(string, "md5");
        }
    }

}
