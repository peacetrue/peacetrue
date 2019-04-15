package com.github.peacetrue.enums;

import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 使用simpleName作为枚举的名称，simpleName重复后使用全限定名称
 *
 * @author xiayx
 */
public class SimpleEnumNameResolver implements EnumNameResolver {

    /** 名称缓存 */
    private Set<String> nameCache = new HashSet<>();

    @Override
    public String resolveEnumName(Class<? extends Enum> enumClass) {
        String name = StringUtils.uncapitalize(enumClass.getSimpleName());
        if (nameCache.contains(name)) return enumClass.getName();//防止simpleName重名
        nameCache.add(name);
        return name;
    }
}
