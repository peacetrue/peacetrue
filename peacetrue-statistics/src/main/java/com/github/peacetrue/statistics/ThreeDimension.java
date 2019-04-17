package com.github.peacetrue.statistics;

import lombok.Data;

/**
 * 三维数据
 *
 * @author xiayx
 * @see TwoDimension
 */
@Data
public class ThreeDimension<X, Y> extends TwoDimension<X, Y> {

    /** 类型 */
    private String type;

    public ThreeDimension() {
    }

    public ThreeDimension(X xAxis, String type, Y yAxis) {
        super(xAxis, yAxis);
        this.type = type;
    }
}
