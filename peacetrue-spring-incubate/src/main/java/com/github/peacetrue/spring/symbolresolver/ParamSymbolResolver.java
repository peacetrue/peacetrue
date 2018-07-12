package com.github.peacetrue.spring.symbolresolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;

/**
 * get symbol from param or path, param first then path
 *
 * @author xiayx
 */
public class ParamSymbolResolver implements SymbolResolver<String> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String paramName;

    public ParamSymbolResolver(String paramName) {
        this.paramName = Objects.requireNonNull(paramName);
    }

    public String resolveSymbol(NativeWebRequest request) {
        String symbol = request.getParameter(paramName);
        logger.debug("got symbol '{}' by param '{}'", symbol, paramName);
        if (StringUtils.isEmpty(symbol)) {
            throw new ResolveException("can't get symbol from request");
        }
        return symbol;
    }

}
