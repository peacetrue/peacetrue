package com.github.peacetrue.result.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * the tests for {@link DataResultException}
 * @author xiayx
 */
public class DataResultExceptionTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void name() throws Exception {
        String value = objectMapper.writeValueAsString(new DataResultException("0000", "0000", "data"));
        System.out.println(value);
    }
}