package com.github.peacetrue.util;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertArrayEquals(arguments, extractValue);
    }

}