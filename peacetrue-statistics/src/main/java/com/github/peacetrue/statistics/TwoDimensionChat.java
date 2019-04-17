package com.github.peacetrue.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 二维图表
 *
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoDimensionChat<X, Y> {

    /** x轴 */
    private List<X> xAxises;
    /** y轴 */
    private List<Y> yAxises;

}
