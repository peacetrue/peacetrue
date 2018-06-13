package com.github.peacetrue.util;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(array[1], Arrays.stream(array).collect(CollectorUtils.reducingToLast(defaults)));
        Assert.assertEquals(defaults, Arrays.stream(new String[]{}).collect(CollectorUtils.reducingToLast(defaults)));
    }

}