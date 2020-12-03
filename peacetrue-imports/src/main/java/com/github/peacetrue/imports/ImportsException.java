package com.github.peacetrue.imports;

/**
 * @author xiayx
 */
public class ImportsException extends RuntimeException {

    public ImportsException() {
    }

    public ImportsException(String message) {
        super(message);
    }

    public ImportsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportsException(Throwable cause) {
        super(cause);
    }

    public ImportsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
