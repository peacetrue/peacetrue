package com.github.peacetrue.spring.abstractmodelattribute;

import com.github.peacetrue.spring.symbolresolver.SymbolResolver;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;

/**
 * first resolve a string symbol from {@link NativeWebRequest},
 * then resolve concrete model attribute type from symbol
 *
 * @author xiayx
 */
public class SymbolConcreteTypeResolver implements ServletAbstractModelAttributeMethodProcessor.ConcreteTypeResolver {

    private SymbolResolver<Class> symbolResolver;

    public SymbolConcreteTypeResolver(SymbolResolver<Class> symbolResolver) {
        this.symbolResolver = Objects.requireNonNull(symbolResolver);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> resolveConcreteType(NativeWebRequest request, Class<T> beanClass) {
        try {
            Class concreteType = symbolResolver.resolveSymbol(request);
            if (!beanClass.isAssignableFrom(concreteType)) {
                throw new ResolveException("the concrete type '" + concreteType.getName()
                        + "' is not subclass of special type '" + beanClass.getName() + "'");
            }
            return concreteType;
        } catch (SymbolResolver.ResolveException e) {
            throw new ResolveException("resolve fail for SymbolResolver.ResolveException", e);
        }
    }

}
