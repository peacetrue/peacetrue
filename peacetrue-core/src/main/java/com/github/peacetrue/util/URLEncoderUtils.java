package com.github.peacetrue.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * a util class for {@link java.net.URLEncoder}
 *
 * @author xiayx
 */
public abstract class URLEncoderUtils {

    /**
     * similar to {@link URLDecoder#decode(String, String)},
     * but avoid {@link UnsupportedEncodingException}
     *
     * @param string the {@code String} to decode
     * @param encode The name of a supported
     * @return the newly decoded {@code String}
     */
    public static String encode(String string, String encode) {
        try {
            return URLEncoder.encode(string, encode);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * similar to {@link #encode(String, String)},
     * default encode to UTF-8
     *
     * @param string the {@code String} to decode
     * @return the newly decoded {@code String}
     */
    public static String encode(String string) {
        return encode(string, StandardCharsets.UTF_8.name());
    }
}
