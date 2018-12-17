package com.github.peacetrue.result;

import java.lang.annotation.*;

/**
 * 不需要使用{@link Result}包裹
 *
 * @author xiayx
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithoutResultWrapper {

}
