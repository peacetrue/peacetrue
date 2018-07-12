package com.github.peacetrue.result;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

/**
 * a Resolver for {@link Result#getCode()}
 *
 * @author xiayx
 */
public interface ResultCodeConverter {

    /**
     * convert the standard result code to custom result code.
     * the standard result code in {@link ResultType}, {@link FailureType} or any other undetermined Rule,
     * the custom result code is something you want to display.
     * e.g. make standard code 'failure' as '10000'
     *
     * @param code the standard result code
     * @return the custom result code
     */
    @Nullable
    String convert(String code);

    /**
     * convert annotation to custom result code
     *
     * @param annotation the annotation
     * @return the custom result code
     */
    @Nullable
    default String convert(Annotation annotation) {
        return annotation.annotationType().getSimpleName();
    }

}
