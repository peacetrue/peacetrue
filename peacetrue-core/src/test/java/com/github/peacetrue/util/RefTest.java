package com.github.peacetrue.util;

import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-12-19 20:58
 **/
class RefTest {
    @Test
    void basic() {
        Ref<Integer> ref = new Ref<>(1);
        new Thread(() -> ref.current++).start();
    }
}
