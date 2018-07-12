package com.github.peacetrue.result;

import javax.servlet.http.HttpServletRequest;

/**
 * used to convert exception page
 *
 * @author xiayx
 */
public interface ExceptionPageResolver {
    /**
     * convert exception page
     *
     * @param request   the request
     * @param exception the exception
     * @return the failure page
     */
    String resolve(HttpServletRequest request, Exception exception);
}
