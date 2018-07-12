package com.github.peacetrue.result.exception;

import com.github.peacetrue.util.CollectionUtils;
import com.github.peacetrue.util.RegexUtils;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * convert data from {@link Exception#getMessage()}
 *
 * @author xiayx
 */
public abstract class ResolveExceptionConverter<T extends Exception, D> extends AbstractExceptionConverter<T, D> {

    @Nullable
    @Override
    protected D resolveData(T exception) {
        return convertArguments(resolveArguments(exception.getMessage()));
    }

    protected String[] resolveArguments(String message) {
        for (Pattern pattern : getPatterns()) {
            String[] arguments = RegexUtils.extractValue(pattern, message);
            if (arguments.length > 0) return arguments;
        }
        return CollectionUtils.emptyArray(String.class);
    }

    protected abstract Pattern[] getPatterns();

    protected abstract D convertArguments(String[] arguments);
}
