package com.github.peacetrue.sign.server;

import com.github.peacetrue.sign.Signed;

/**
 * used when sign is invalid
 *
 * @author xiayx
 */
public class InvalidSignException extends RuntimeException {

    private Signed signed;

    public InvalidSignException(Signed signed) {
        super("the sign '" + signed.getSign() + "' is invalid");
        this.signed = signed;
    }

    public Signed getSigned() {
        return signed;
    }

}
