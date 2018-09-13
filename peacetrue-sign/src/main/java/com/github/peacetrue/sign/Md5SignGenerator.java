package com.github.peacetrue.sign;

import com.github.peacetrue.security.MessageDigestUtils;

/**
 * md5签名生成器
 *
 * @author xiayx
 */
public class Md5SignGenerator implements SignGenerator {

    /**
     * <ul>
     * <li>串联参数和秘钥生成字符串A</li>
     * <li>md5加密A生成字节数组B</li>
     * <li>通过hexBinary方式将B转换为字符串</li>
     * </ul>
     */
    @Override
    public String generate(String params, String secret) {
        return MessageDigestUtils.encode(MessageDigestUtils.getMD5(), params + secret);
    }
}
