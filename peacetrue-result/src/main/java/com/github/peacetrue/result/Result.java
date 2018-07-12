package com.github.peacetrue.result;


import com.github.peacetrue.core.Code;
import com.github.peacetrue.core.Message;

/**
 * the response result that server output to client
 *
 * @author xiayx
 */
public interface Result extends Code, Message {

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
