package com.github.peacetrue.spring.entityclass;

import com.github.peacetrue.spring.symbolresolver.SymbolResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Objects;

/**
 * @author xiayx
 */
public class EntityClassSymbolResolver implements SymbolResolver<Class> {

    private static final String CACHE = EntityClassSymbolResolver.class.getName() + ".cache";

    private SymbolResolver<String> symbolResolver;
    private EntityClassResolver entityClassResolver;

    public EntityClassSymbolResolver(SymbolResolver<String> symbolResolver, EntityClassResolver entityClassResolver) {
        this.symbolResolver = Objects.requireNonNull(symbolResolver);
        this.entityClassResolver = Objects.requireNonNull(entityClassResolver);
    }

    @Override
    public Class resolveSymbol(NativeWebRequest request) throws ResolveException {
        Class entityClass = (Class) request.getAttribute(CACHE, RequestAttributes.SCOPE_REQUEST);
        if (entityClass == null) {
            String symbol = symbolResolver.resolveSymbol(request);
            entityClass = entityClassResolver.resolveEntityClass(symbol);
            if (entityClass == null) throw new ResolveException("can't get entity class from symbol '" + symbol + "'");
            request.setAttribute(CACHE, entityClass, RequestAttributes.SCOPE_REQUEST);
        }
        return entityClass;
    }
}
