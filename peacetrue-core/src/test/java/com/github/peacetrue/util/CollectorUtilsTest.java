package com.github.peacetrue.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * tests for {@link CollectorUtils}
 *
 * @author xiayx
 */
public class CollectorUtilsTest {

    @Test
    public void reducingToLast() throws Exception {
        String[] array = {"1", "2"};
        String defaults = "0";
        Assertions.assertEquals(array[1], Arrays.stream(array).collect(CollectorUtils.reducingToLast(defaults)));
        Assertions.assertEquals(defaults, Arrays.stream(new String[]{}).collect(CollectorUtils.reducingToLast(defaults)));
    }

}
