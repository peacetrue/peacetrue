package com.github.peacetrue.spring.entityclass;

import com.github.peacetrue.spring.symbolresolver.PathSymbolResolver;
import com.google.common.base.CaseFormat;
import org.springframework.web.context.request.NativeWebRequest;

import static com.google.common.base.CaseFormat.UPPER_CAMEL;

/**
 * resolver entity name from request
 *
 * @author xiayx
 */
public class EntityNameResolver extends PathSymbolResolver {

    public EntityNameResolver() {
    }

    public EntityNameResolver(int pathVarIndex) {
        super(pathVarIndex);
    }

    @Override
    public String resolveSymbol(NativeWebRequest request) {
        return formatSymbol(super.resolveSymbol(request));
    }

    protected String formatSymbol(String symbol) {
        //snake to pascal e.g. how_to_ask->HowToAsk
        String entityName = symbol;
        if (symbol.contains("-")) {
            entityName = CaseFormat.LOWER_HYPHEN.to(UPPER_CAMEL, symbol);
        } else if (symbol.contains("_")) {
            entityName = CaseFormat.LOWER_UNDERSCORE.to(UPPER_CAMEL, symbol);
        }
        logger.debug("got entity name '{}' from symbol '{}'", entityName, symbol);
        return entityName;
    }

}
