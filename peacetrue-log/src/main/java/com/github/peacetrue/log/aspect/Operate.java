package com.github.peacetrue.log.aspect;

import java.lang.annotation.*;

/**
 * 操作
 *
 * @author xiayx
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Operate {

    /** 模块 */
    Module module() default @Module(code = "");

    /** 操作编码 */
    String code();

}