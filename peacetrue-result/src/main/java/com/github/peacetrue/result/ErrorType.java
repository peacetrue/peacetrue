package com.github.peacetrue.result;

/**
 * the type of error
 *
 * @author xiayx
 * @see ResultType
 */
public enum ErrorType {

    //metadata
    exception_converter_error,
    //web
    argument_error,
    argument_type_mismatch,
    argument_format_mismatch,
    //db
    sql_exception,
    duplicate_key,

}
