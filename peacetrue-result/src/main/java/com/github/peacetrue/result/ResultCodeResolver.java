package com.github.peacetrue.result;

import java.lang.annotation.Annotation;

/**
 * a Resolver for {@link Result#getCode()}
 *
 * @author xiayx
 */
public interface ResultCodeResolver {

    /**
     * resolve the standard result code to custom result code.
     * the standard result code in {@link ResultType}, {@link ErrorType} or any other undetermined Rule,
     * the custom result code is something you want to display.
     * e.g. make standard code 'error' as '10000'
     *
     * @param code the standard result code
     * @return the custom result code
     */
    String resolve(String code);

    /**
     * resolve annotation to custom result code
     *
     * @param annotation the annotation
     * @return the custom result code
     */
    default String resolve(Annotation annotation) {
        return annotation.annotationType().getSimpleName();
    }

}
