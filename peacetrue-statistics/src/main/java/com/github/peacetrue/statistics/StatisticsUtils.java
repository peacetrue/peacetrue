package com.github.peacetrue.statistics;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 统计工具类
 *
 * @author xiayx
 */
public abstract class StatisticsUtils {

    /** 使用默认值构建二维图表 */
    public static <X, Y> List<TwoDimension<X, Y>> buildTwoDimension(List<X> xAxises, Y defaults) {
        Objects.requireNonNull(defaults);
        return xAxises.stream().map(xAxis -> new TwoDimension<>(xAxis, defaults)).collect(Collectors.toList());
    }

    /** 使用数据填充二维图表 */
    public static <X, Y> void fillTwoDimension(List<TwoDimension<X, Y>> placeholder, @Nullable List<TwoDimension<X, Y>> data) {
        if (data == null || data.isEmpty()) return;
        for (int i = 0; i < placeholder.size(); i++) {
            for (TwoDimension<X, Y> item : data) {
                if (item.getXAxis().equals(placeholder.get(i).getXAxis())) {
                    placeholder.set(i, item);
                    break;
                }
            }
        }
    }

    /** 使用默认值构建二维图表 */
    public static <X, Y> TwoDimensionChat<X, Y> buildTwoDimensionChat(List<X> xAxises, Y defaults) {
        Objects.requireNonNull(defaults);
        TwoDimensionChat<X, Y> twoDimension = new TwoDimensionChat<>();
        twoDimension.setXAxises(xAxises);
        twoDimension.setYAxises(getPlaceholderYAxises(xAxises, defaults));
        return twoDimension;
    }

    private static <X, Y> List<Y> getPlaceholderYAxises(List<X> xAxises, Y defaults) {
        return xAxises.stream().map(xAxis -> defaults).collect(Collectors.toList());
    }

    /** 使用数据填充二维图表 */
    public static <X, Y> void fillTwoDimensionChat(TwoDimensionChat<X, Y> placeholder, @Nullable TwoDimensionChat<X, Y> data) {
        if (data == null) return;
        for (int i = 0; i < placeholder.getXAxises().size(); i++) {
            X xAxis = placeholder.getXAxises().get(i);
            for (int j = 0; j < data.getXAxises().size(); j++) {
                if (xAxis.equals(data.getXAxises().get(j))) {
                    placeholder.getYAxises().set(i, data.getYAxises().get(j));
                }
            }
        }
    }

    /** 使用默认值构建三维图表 */
    public static <X, Y> ThreeDimensionChat<X, Y> buildThreeDimensionChat(List<X> xAxises, List<String> types, Y defaults) {
        List<Y> placeholders = getPlaceholderYAxises(xAxises, Objects.requireNonNull(defaults));
        ThreeDimensionChat<X, Y> chat = new ThreeDimensionChat<>();
        chat.setXAxises(xAxises);
        chat.setYAxises(types.stream().map(type -> new ThreeDimensionChat.YAxis<>(type, placeholders)).collect(Collectors.toList()));
        return chat;
    }

    /** 使用数据填充三维图表 */
    public static <X, Y> void fillThreeDimensionChat(ThreeDimensionChat<X, Y> chat, @Nullable List<ThreeDimension<X, Y>> data) {
        if (data == null) return;
        data.forEach(data_ -> {
            int index = chat.getXAxises().indexOf(data_.getXAxis());
            chat.getYAxises().stream().filter(chat_ -> data_.getType().equals(chat_.getType()))
                    .findAny().ifPresent(item -> item.getValues().set(index, data_.getYAxis()));
        });
    }


}
