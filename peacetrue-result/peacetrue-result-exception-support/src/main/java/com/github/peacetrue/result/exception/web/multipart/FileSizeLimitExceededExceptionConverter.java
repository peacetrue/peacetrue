package com.github.peacetrue.result.exception.web.multipart;

import com.github.peacetrue.result.exception.SimplifiedExceptionConverter;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;

import javax.annotation.Nullable;

/**
 * 文件大小超出异常
 *
 * @author xiayx
 */
public class FileSizeLimitExceededExceptionConverter extends SimplifiedExceptionConverter<FileSizeLimitExceededException> {

    @Nullable
    @Override
    protected Object[] resolveData(FileSizeLimitExceededException exception) {
        return new Object[]{exception.getFileName(), MaxUploadSizeExceededExceptionConverter.humanReadableByteCount(exception.getPermittedSize(), false)};
    }

}
