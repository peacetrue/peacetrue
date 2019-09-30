package com.github.peacetrue.result.exception.web.multipart;

import com.github.peacetrue.result.exception.SimplifiedExceptionConverter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.annotation.Nullable;

/**
 * 文件大小超出异常
 *
 * @author xiayx
 */
public class MaxUploadSizeExceededExceptionConverter extends SimplifiedExceptionConverter<MaxUploadSizeExceededException> {

    public static String formatSize(long v) {
        if (v < 1024) return v + " B";
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return String.format("%.1f %sB", (double) v / (1L << (z * 10)), " KMGTPE".charAt(z));
    }

    public static String humanReadableByteCount(long bytes) {
        return humanReadableByteCount(bytes, true);
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    @Nullable
    @Override
    protected Object[] resolveData(MaxUploadSizeExceededException exception) {
        return new Object[]{humanReadableByteCount(exception.getMaxUploadSize())};
    }

}
