package com.github.peacetrue.log.aspect;

/**
 * 创建者主键提供者
 *
 * @author xiayx
 */
public interface CreatorIdProvider<CreatorId> {
    /** 获取创建者主键 */
    CreatorId get(LogBuilder.Context context);
}
