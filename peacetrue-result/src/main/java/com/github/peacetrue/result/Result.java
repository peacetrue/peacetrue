package com.github.peacetrue.result;


/**
 * the response result that server output to client
 *
 * @author xiayx
 */
public interface Result {

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
