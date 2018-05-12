package com.github.peacetrue.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Sha1Encoder
 *
 * @author xiayx
 */
public class Sha1Encoder implements Encoder {

    public final static Sha1Encoder DEFAULT = new Sha1Encoder();

    private Sha1Encoder() {}

    @Override
    public String encode(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(string.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new EncodeException(string, "SHA-1");
        }
    }

}
