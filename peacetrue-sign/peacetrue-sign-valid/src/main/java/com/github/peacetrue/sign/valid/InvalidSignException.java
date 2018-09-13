package com.github.peacetrue.sign.valid;

/**
 * used when sign is invalid
 *
 * @author xiayx
 */
public class InvalidSignException extends RuntimeException {

    private Object signed;

    public InvalidSignException(Object signed) {
        super("the signed value '" + signed + "' is invalid");
        this.signed = signed;
    }

    public Object getSigned() {
        return signed;
    }

}
