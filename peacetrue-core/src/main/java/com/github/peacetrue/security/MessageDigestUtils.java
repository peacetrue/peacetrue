package com.github.peacetrue.security;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * a util class for {@link MessageDigest}
 *
 * @author xiayx
 */
public abstract class MessageDigestUtils {

    /** the standard {@code MessageDigest} algorithms: */
    public static final String
            ALGORITHM_MD5 = "MD5",
            ALGORITHM_SHA1 = "SHA-1",
            ALGORITHM_SHA256 = "SHA-256";

    /**
     * get the instance of {@link MessageDigest}
     *
     * @param algorithm the message digest algorithm
     * @return the MessageDigest
     */
    public static MessageDigest getInstance(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(algorithm + " not supported", e);
        }
    }

    /**
     * get MD5 MessageDigest
     *
     * @return MD5 MessageDigest
     */
    public static MessageDigest getMD5() {
        return getInstance(ALGORITHM_MD5);
    }

    /**
     * get SHA1 MessageDigest
     *
     * @return get SHA1 MessageDigest
     */
    public static MessageDigest getSHA1() {
        return getInstance(ALGORITHM_SHA1);
    }

    /**
     * get SHA256 MessageDigest
     *
     * @return get SHA256 MessageDigest
     */
    public static MessageDigest getSHA256() {
        return getInstance(ALGORITHM_SHA256);
    }


    /**
     * encode a string to string
     *
     * @param messageDigest the messageDigest
     * @param string        the string needed encode
     * @return the encoded string
     */
    public static String encode(MessageDigest messageDigest, String string) {
        byte[] digest = messageDigest.digest(string.getBytes());
        return DatatypeConverter.printHexBinary(digest);
    }
}
