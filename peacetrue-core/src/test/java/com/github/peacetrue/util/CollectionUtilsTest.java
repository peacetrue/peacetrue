package com.github.peacetrue.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests for {@link CollectionUtils}
 *
 * @author xiayx
 */
public class CollectionUtilsTest {
    @Test
    public void detectElementTypeForIterable() throws Exception {
        //null is not allowed
        AssertUtils.assertException(() -> CollectionUtils.detectElementType((Iterable<?>) null));
        //empty is not allowed
        AssertUtils.assertException(() -> CollectionUtils.detectElementType(Collections.emptyList()));
        //first null element is not allowed
        AssertUtils.assertException(() -> CollectionUtils.detectElementType(Collections.singleton(null)));

        //equals to first element type
        List<Object> list = Arrays.asList(1, 1L, 1F, 1D);
        Class<?> elementType = CollectionUtils.detectElementType(list);
        assertEquals(list.get(0).getClass(), elementType);
    }

    @Test
    public void detectElementTypeForArray() throws Exception {
        //null is not allowed
        AssertUtils.assertException(() -> CollectionUtils.detectElementType((Object[]) null));
        //empty is not allowed
        AssertUtils.assertException(() -> CollectionUtils.detectElementType(new Object[]{}));
        //first null element is not allowed
        AssertUtils.assertException(() -> CollectionUtils.detectElementType(new Object[]{null}));

        //equals to first element type
        Object[] array = new Object[]{1, 1L, 1F, 1D};
        Class<?> elementType = CollectionUtils.detectElementType(array);
        assertEquals(array[0].getClass(), elementType);
    }

}
