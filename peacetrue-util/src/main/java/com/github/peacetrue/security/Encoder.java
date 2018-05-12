package com.github.peacetrue.security;

/**
 * 编码器
 *
 * @author xiayx
 */
public interface Encoder {

    /**
     * 将一个字符串进行编码
     *
     * @param string 字符串
     * @return 编码后的字符串
     */
    String encode(String string) throws EncodeException;

    /**
     * 判断before在编码后是否和after相等
     *
     * @param before 编码前的字符串
     * @param after  编码后的字符串
     * @return true, 如果相等; 否则,false
     */
    default boolean equals(String before, String after) throws EncodeException {
        return encode(before).equals(after);
    }
}
