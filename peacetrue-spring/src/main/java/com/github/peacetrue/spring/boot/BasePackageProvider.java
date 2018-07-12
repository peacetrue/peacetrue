package com.github.peacetrue.spring.boot;

/**
 * to provide a base package of an application
 *
 * @author xiayx
 */
public interface BasePackageProvider {

    /**
     * get the base package of an application
     *
     * @return the base package
     * @throws IllegalStateException when can't find a base package
     */
    String getBasePackage();

}
