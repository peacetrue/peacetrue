package com.github.peacetrue.result;

import javax.servlet.http.HttpServletRequest;

/**
 * used to resolve error page
 *
 * @author xiayx
 */
public interface ErrorPageResolver {
    /**
     * resolve error page
     *
     * @param request   the request
     * @param exception the exception
     * @return the error page
     */
    String resolve(HttpServletRequest request, Exception exception);
}
