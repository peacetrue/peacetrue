package com.github.peacetrue.result;


import java.io.Serializable;

/**
 * the response result that server output to client
 *
 * @author xiayx
 */
public interface Result extends Serializable {

    /**
     * get result code
     *
     * @return a unique identifier for program
     */
    String getCode();

    /**
     * get result message
     *
     * @return a description for human
     */
    String getMessage();
}
