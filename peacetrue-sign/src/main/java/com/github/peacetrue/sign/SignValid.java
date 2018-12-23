package com.github.peacetrue.sign;

import java.lang.annotation.*;

/**
 * 标注在方法上，表示此方法需要验证签名
 *
 * @author xiayx
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignValid {

}