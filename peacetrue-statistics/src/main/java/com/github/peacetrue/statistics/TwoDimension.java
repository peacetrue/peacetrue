package com.github.peacetrue.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二维数据
 *
 * @author xiayx
 * @see TwoDimensionChat
 * @see ThreeDimension
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoDimension<X, Y> {

    /** x轴 */
    private X xAxis;
    /** y轴 */
    private Y yAxis;

}
