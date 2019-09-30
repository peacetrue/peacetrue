package com.github.peacetrue.uuid;

import java.util.UUID;

/**
 * 系统的唯一标识生成器。
 * 每次都产生不同的唯一标识，永远不会重复，可在分布式环境中使用。
 * 唯一标识长度32位，包含数字和字母，无序
 *
 * @author xiayx
 */
public class SystemUUIDGenerator implements UUIDGenerator<String> {

    @Override
    public String next() throws UUIDOverflowException {
        //减少4个中划线
        return UUID.randomUUID().toString().replace("-", "");
    }
}
