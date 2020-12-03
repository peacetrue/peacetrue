package com.github.peacetrue.util;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiayx
 */
public class ComparableUtilsTest {

    @Test
    public void findValueBetweenRange() throws Exception {
        List<Integer> integers = ComparableUtils.findValueBetweenRange(1, 10, integer -> integer + 1);
        Assertions.assertEquals(integers, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }


}
