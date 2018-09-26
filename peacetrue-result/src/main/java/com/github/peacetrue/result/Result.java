package com.github.peacetrue.result;


import com.github.peacetrue.core.CodeCapable;
import com.github.peacetrue.core.MessageCapable;

/**
 * the response result that server output to client
 *
 * @author xiayx
 */
public interface Result extends CodeCapable, MessageCapable {

    /**
     * get result code
     *
     * @return a unique identifier for program
     */
    @Override
    String getCode();

    /**
     * get result message
     *
     * @return a description for human
     */
    @Override
    String getMessage();
}
