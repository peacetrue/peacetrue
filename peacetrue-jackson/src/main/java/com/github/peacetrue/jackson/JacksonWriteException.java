package com.github.peacetrue.jackson;

/**
 * @author xiayx
 */
public class JacksonWriteException extends RuntimeException {
    public JacksonWriteException() {
    }

    public JacksonWriteException(String message) {
        super(message);
    }

    public JacksonWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public JacksonWriteException(Throwable cause) {
        super(cause);
    }

    public JacksonWriteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
