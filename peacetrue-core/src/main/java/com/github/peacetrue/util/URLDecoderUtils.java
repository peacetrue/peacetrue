package com.github.peacetrue.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * a util class for {@link java.net.URLDecoder}
 *
 * @author xiayx
 */
public abstract class URLDecoderUtils {

    /**
     * similar to {@link URLDecoder#decode(String, String)},
     * but avoid {@link UnsupportedEncodingException}
     *
     * @param string the {@code String} to decode
     * @param encode The name of a supported
     * @return the newly decoded {@code String}
     */
    public static String decode(String string, String encode) {
        try {
            return URLDecoder.decode(string, encode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * similar to {@link #decode(String, String)},
     * default encode to UTF-8
     *
     * @param string the {@code String} to decode
     * @return the newly decoded {@code String}
     */
    public static String decode(String string) {
        return decode(string, StandardCharsets.UTF_8.name());
    }
}
