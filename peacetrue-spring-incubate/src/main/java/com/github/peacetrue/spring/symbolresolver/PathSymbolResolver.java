package com.github.peacetrue.spring.symbolresolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * path variable represent a symbol
 *
 * @author xiayx
 */
public class PathSymbolResolver implements SymbolResolver<String> {

    private static final String[] EMPTY = new String[0];

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private Integer pathVarIndex;

    public PathSymbolResolver() {
        this(0);
    }

    public PathSymbolResolver(int pathVarIndex) {
        if (pathVarIndex < 0) throw new IllegalArgumentException("the pathVarIndex must >= 0, but actual " + pathVarIndex);
        this.pathVarIndex = pathVarIndex;
    }

    public String resolveSymbol(NativeWebRequest request) {
        String[] pathVars = getPathVariables(request);
        if (pathVarIndex >= pathVars.length) {
            throw new ResolveException(String.format("the path variable index %s out of range in %s", pathVarIndex, Arrays.toString(pathVars)));
        }

        String symbol = StringUtils.trimWhitespace(pathVars[pathVarIndex]);
        logger.debug("got symbol '{}' at index {}", symbol, pathVarIndex);
        return symbol;
    }

    protected String[] getPathVariables(NativeWebRequest request) {
        HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
        String path = httpServletRequest.getRequestURI();
        logger.debug("got request uri '{}'", path);

        String[] pathVariables = path == null || path.length() <= 1
                ? EMPTY
                : path.substring(1).split("/");//path start with '/', remove it
        logger.debug("got path variables {}", Arrays.toString(pathVariables));

        return pathVariables;
    }


}
