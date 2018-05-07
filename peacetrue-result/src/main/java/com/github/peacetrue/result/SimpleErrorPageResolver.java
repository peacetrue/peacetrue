package com.github.peacetrue.result;

import javax.servlet.http.HttpServletRequest;

/**
 * return a uniquely determined page
 *
 * @author xiayx
 */
public class SimpleErrorPageResolver implements ErrorPageResolver {

    private String errorPage;

    @Override
    public String resolve(HttpServletRequest request, Exception exception) {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
