package com.github.peacetrue.uuid;

/**
 * 唯一标识资源库
 *
 * @author xiayx
 */
public interface UUIDRepository {

    /** 获取指定场景下的标识 */
    Long get(String context);

    /** 设置指定场景下的标识 */
    void set(String context, Long id);

}
