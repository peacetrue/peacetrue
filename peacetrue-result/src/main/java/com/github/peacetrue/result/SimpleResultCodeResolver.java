package com.github.peacetrue.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * the implement of {@link ResultCodeResolver}
 *
 * @author xiayx
 */
public class SimpleResultCodeResolver implements ResultCodeResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /** standard result code to custom result code */
    private Map<String, String> codes = Collections.emptyMap();

    @Override
    public String resolve(String code) {
        logger.info("resolve the standard result code '{}'", code);
        return codes.getOrDefault(code, code);
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = Objects.requireNonNull(codes);
    }
}
