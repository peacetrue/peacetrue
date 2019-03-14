package com.github.peacetrue.util;

import java.util.UUID;

/**
 * @author xiayx
 */
public abstract class UUIDUtils {

    /** 生成唯一编号 */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
