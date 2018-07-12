package com.github.peacetrue.result;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * the implement of {@link ResultCodeConverter}
 *
 * @author xiayx
 */
public class SimpleResultCodeResolver implements ResultCodeConverter {

    /** standard result code to custom result code */
    private Map<String, String> codes = Collections.emptyMap();

    @Override
    public String convert(String code) {
        return codes.get(code);
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = Objects.requireNonNull(codes);
    }
}
