package com.github.peacetrue.tree.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author xiayx
 */
public class TreeNodeGeneratorTest {

    /** 计算等比数列公式 */
    public static double geometricSum(double a, double q, double n) {
        if (q == 1) return n * a;
        return a * (1 - Math.pow(q, n)) / (1 - q);
    }

    /** 计算等比数列公式 */
    public static double geometricSum(double q, double n) {
        return geometricSum(1, q, n);
    }

    @Test
    public void generate() throws Exception {
        //等比数列公式
        //1 + 3 + 3^2 ... 3^n
        TreeNodeGenerator<Integer> generator = new TreeNodeGenerator<>(3, 3,
                new TreeNodeBuilder<Integer>() {
                    private int i = 0;

                    public Integer build(Integer parent, int index, int level) {
                        return i++;
                    }
                });
        List<Integer> integers = generator.generate();
        Assertions.assertEquals(integers.size(), (int) geometricSum(3, 3));
    }

}
