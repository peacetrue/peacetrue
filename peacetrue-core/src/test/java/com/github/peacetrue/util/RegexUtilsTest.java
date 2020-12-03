package com.github.peacetrue.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * tests for {@link RegexUtils}
 *
 * @author xiayx
 */
public class RegexUtilsTest {
    @Test
    public void extractValue() throws Exception {
        String format = "expected '%s' but got '%s'";
        Object[] arguments = {"a", "b"};
        String message = String.format(format, arguments);
        String regex = String.format(format, "([^']+)", "([^']+)");
        String[] extractValue = RegexUtils.extractValue(regex, message);
        Assertions.assertArrayEquals(arguments, extractValue);
    }

}
