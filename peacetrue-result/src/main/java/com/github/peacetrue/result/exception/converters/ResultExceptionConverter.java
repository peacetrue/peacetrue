package com.github.peacetrue.result.exception.converters;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.exception.ResultException;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * for {@link ResultException}
 *
 * @author xiayx
 */
public class ResultExceptionConverter implements ExceptionConverter<ResultException> {

    @Override
    public Result convert(ResultException exception, @Nullable Locale locale) {
        return ResultUtils.wrap(exception);
    }
}
