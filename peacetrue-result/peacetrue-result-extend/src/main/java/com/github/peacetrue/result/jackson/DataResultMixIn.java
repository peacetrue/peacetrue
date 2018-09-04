package com.github.peacetrue.result.jackson;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author xiayx
 * @see com.github.peacetrue.result.DataResult
 */
public interface DataResultMixIn<T> {

    @JsonView(DataResultView.class)
    String getCode();

    @JsonView(DataResultView.class)
    String getMessage();

    @JsonView(DataResultView.class)
    T getData();
}
