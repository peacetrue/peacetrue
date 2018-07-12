package com.github.peacetrue.spring.symbolresolver;

import org.springframework.web.context.request.NativeWebRequest;

/**
 * symbol can be anything that you want got from request
 */
public interface SymbolResolver<T> {

    /**
     * resolve a symbol from {@link NativeWebRequest}
     *
     * @param request must not be null
     * @return never be null
     * @throws ResolveException when can't resolve
     */
    T resolveSymbol(NativeWebRequest request) throws ResolveException;

    /** the exception for {@link #resolveSymbol(NativeWebRequest)} */
    class ResolveException extends RuntimeException {

        public ResolveException(String message) {
            super(message);
        }

        public ResolveException(String message, Throwable cause) {
            super(message, cause);
        }
    }


}
