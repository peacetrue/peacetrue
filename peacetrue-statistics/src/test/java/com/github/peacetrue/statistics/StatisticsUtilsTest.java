package com.github.peacetrue.statistics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xiayx
 */
public class StatisticsUtilsTest {

    @Test
    public void buildTwoDimension() throws Exception {
        List<String> xAxises = IntStream.rangeClosed(1, 9).mapToObj(i -> ("2019-01-0" + i)).collect(Collectors.toList());
        List<TwoDimension<String, Integer>> twoDimensions = StatisticsUtils.buildTwoDimension(xAxises, 0);
        Assertions.assertEquals(twoDimensions.get(0).getYAxis(), (Integer) 0);
    }

    @Test
    public void fillTwoDimension() throws Exception {
        List<String> xAxises = IntStream.rangeClosed(1, 9).mapToObj(i -> ("2019-01-0" + i)).collect(Collectors.toList());
        List<TwoDimension<String, Integer>> twoDimensions = StatisticsUtils.buildTwoDimension(xAxises, 0);
        StatisticsUtils.fillTwoDimension(twoDimensions, Arrays.asList(new TwoDimension<>("2019-01-01", 1)));
        Assertions.assertEquals(twoDimensions.get(0).getYAxis(), (Integer) 1);
        Assertions.assertEquals(twoDimensions.get(1).getYAxis(), (Integer) 0);

    }

    @Test
    public void buildThreeDimensionChat() throws Exception {
        List<String> xAxises = IntStream.rangeClosed(1, 9).mapToObj(i -> ("2019-01-0" + i)).collect(Collectors.toList());
        List<String> types = Arrays.asList("男", "女");
        ThreeDimensionChat<String, Integer> chat = StatisticsUtils.buildThreeDimensionChat(xAxises, types, 0);
        Assertions.assertEquals(chat.getXAxises(), xAxises);
        Assertions.assertEquals(chat.getYAxises().get(0).getType(), "男");
        Assertions.assertEquals(chat.getYAxises().get(0).getValues().get(0), (Integer) 0);
    }

    @Test
    public void fillThreeDimensionChat() throws Exception {
        List<String> xAxises = IntStream.rangeClosed(1, 9).mapToObj(i -> ("2019-01-0" + i)).collect(Collectors.toList());
        List<String> types = Arrays.asList("男", "女");
        ThreeDimensionChat<String, Integer> chat = StatisticsUtils.buildThreeDimensionChat(xAxises, types, 0);
        StatisticsUtils.fillThreeDimensionChat(chat, Arrays.asList(new ThreeDimension<>("2019-01-01", "男", 1)));
        Assertions.assertEquals(chat.getYAxises().get(0).getValues().get(0), (Integer) 1);
    }

}
