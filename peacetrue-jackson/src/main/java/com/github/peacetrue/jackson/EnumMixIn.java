package com.github.peacetrue.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * the mix in for enum
 *
 * @author xiayx
 */
public interface EnumMixIn {

    @JsonSerialize(using = EnumSerializer.class)
    interface CustomSerializer {}

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    interface ObjectSerializer {}
}
