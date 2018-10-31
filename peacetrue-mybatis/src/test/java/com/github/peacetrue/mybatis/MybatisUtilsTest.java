package com.github.peacetrue.mybatis;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiayx
 */
public class MybatisUtilsTest {

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String stringDateTime = "2000-06-15 12:30:30";

    @Test
    public void endDateValue() throws Exception {
        Date parse = dateTimeFormat.parse(stringDateTime);
        Date date = MybatisUtils.endDateValue(parse);
        String format = dateTimeFormat.format(date);
        Assert.assertEquals("2000-06-15 00:00:00", format);
    }

}