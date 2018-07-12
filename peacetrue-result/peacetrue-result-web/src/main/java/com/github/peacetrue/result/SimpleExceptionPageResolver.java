package com.github.peacetrue.result;

import javax.servlet.http.HttpServletRequest;

/**
 * return a uniquely determined page
 *
 * @author xiayx
 */
public class SimpleExceptionPageResolver implements ExceptionPageResolver {

    private String exceptionPage;

    @Override
    public String resolve(HttpServletRequest request, Exception exception) {
        return exceptionPage;
    }

    public void setExceptionPage(String exceptionPage) {
        this.exceptionPage = exceptionPage;
    }
}
