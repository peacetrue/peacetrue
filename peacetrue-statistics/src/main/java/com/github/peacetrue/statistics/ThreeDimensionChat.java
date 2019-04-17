package com.github.peacetrue.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 三维图表
 *
 * @author xiayx
 */
@Data
@ToString(callSuper = true)
public class ThreeDimensionChat<X, V> extends TwoDimensionChat<X, ThreeDimensionChat.YAxis<V>> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class YAxis<T> {
        private String type;
        private List<T> values;
    }

}
