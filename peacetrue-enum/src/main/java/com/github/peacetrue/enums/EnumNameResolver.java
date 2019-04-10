package com.github.peacetrue.enums;

/**
 * 枚举类名解析器
 *
 * @author xiayx
 */
public interface EnumNameResolver {
    /** 解析枚举类名 */
    String resolveEnumName(Class<? extends Enum> enumClass);
}
