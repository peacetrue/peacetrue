package com.github.peacetrue.jackson;

/**
 * @author xiayx
 */
public class JacksonReadException extends RuntimeException {
    public JacksonReadException() {
    }

    public JacksonReadException(String message) {
        super(message);
    }

    public JacksonReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public JacksonReadException(Throwable cause) {
        super(cause);
    }

    public JacksonReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
