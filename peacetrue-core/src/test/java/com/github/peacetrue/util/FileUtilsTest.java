package com.github.peacetrue.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-12-01 08:30
 **/
class FileUtilsTest {


    @Test
    void formatPath() {
        Assertions.assertEquals("/a/b/c", FileUtils.formatPath("/a/b/c"));
        Assertions.assertEquals("/a/b/c", FileUtils.formatPath("/a/b/c/"));
        Assertions.assertEquals("/a/b/c", FileUtils.formatPath("//a//b//c//"));
    }

    @Test
    void formatRelativePath() {
        Assertions.assertEquals("a/b/c", FileUtils.formatRelativePath("/a/b/c"));
        Assertions.assertEquals("a/b/c", FileUtils.formatRelativePath("/a/b/c/"));
        Assertions.assertEquals("a/b/c", FileUtils.formatRelativePath("//a//b//c//"));
    }
}
