package com.github.peacetrue.result;

/**
 * the type of failure
 *
 * @author xiayx
 * @see ResultType
 */
public enum FailureType {

    client_error,
    parameter_error,
    parameter_missing,
    parameter_type_mismatch,
    parameter_format_mismatch,

    server_error,
}
