package com.github.peacetrue.lock.aspect;

import java.lang.annotation.*;

/**
 * Redis锁，标注于方法上，表示施加Redis锁
 *
 * @author xiayx
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LockPoint {

    /** -1表示未设置过期值，将使用默认的过期值 */
    long EXPIRY_UNSET = -1;

    /** 键值，支持使用SPEL表达式，例如：[0].name，以方法参数数组为根对象 */
    String key();

    /** 锁的过期时间（毫秒） */
    long expiry() default EXPIRY_UNSET;

}