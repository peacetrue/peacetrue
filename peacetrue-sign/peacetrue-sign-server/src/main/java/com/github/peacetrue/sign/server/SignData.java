package com.github.peacetrue.sign.server;

import java.lang.annotation.*;

/**
 * used to check sign for parameter
 *
 * @author xiayx
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignData {

}