package com.github.peacetrue.result.exception.web.multipart;

import com.github.peacetrue.result.exception.SimplifiedExceptionConverter;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.annotation.Nullable;

/**
 * @author xiayx
 */
public class MissingServletRequestPartExceptionConverter extends SimplifiedExceptionConverter<MissingServletRequestPartException> {
    @Nullable
    @Override
    protected Object[] resolveData(MissingServletRequestPartException exception) {
        return new Object[]{exception.getRequestPartName()};
    }
}
