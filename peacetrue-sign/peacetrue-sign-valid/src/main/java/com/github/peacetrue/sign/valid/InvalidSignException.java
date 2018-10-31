package com.github.peacetrue.sign.valid;

/**
 * used when sign is invalid
 *
 * @author xiayx
 */
public class InvalidSignException extends RuntimeException {

    private Object signedValue;

    public InvalidSignException(Object signedValue) {
        super("the signed value '" + signedValue + "' is invalid");
        this.signedValue = signedValue;
    }

    public Object getSignedValue() {
        return signedValue;
    }

}
