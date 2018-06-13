package com.github.peacetrue.festivalschedule;

import com.github.peacetrue.util.DateTimeFormatterUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.MonthDay;
import java.time.Year;
import java.util.*;

/**
 * tests for {@link FestivalScheduleUtils}
 *
 * @author xiayx
 */
public class FestivalScheduleUtilsTest {

    @Test
    public void resolveProperties() throws Exception {
        Year year = Year.of(2018);
        String date = "0101", restdays = "0102,0103", workdays = "0104,0105";
        Map<String, String>  properties = new HashMap<>();
        properties.put(FestivalScheduleUtils.buildPropertyKey(year, Festival.NEW_YEAR, FestivalSchedule.PROPERTY_DATE), date);
        properties.put(FestivalScheduleUtils.buildPropertyKey(year, Festival.NEW_YEAR, FestivalSchedule.PROPERTY_RESTDAYS), restdays);
        properties.put(FestivalScheduleUtils.buildPropertyKey(year, Festival.NEW_YEAR, FestivalSchedule.PROPERTY_WORKDAYS), workdays);

        Map<Year, List<FestivalSchedule>> years = FestivalScheduleUtils.resolveProperties(properties);
        List<FestivalSchedule> festivalSchedules = years.get(year);
        FestivalSchedule festivalSchedule = festivalSchedules.stream()
                .filter(_festivalSchedule -> _festivalSchedule.getFestival().equals(Festival.NEW_YEAR))
                .findFirst().orElseThrow(IllegalArgumentException::new);
        Assert.assertEquals(resolve(date), festivalSchedule.getDate());
        Assert.assertArrayEquals(resolve(restdays.split(",")), festivalSchedule.getRestdays());
        Assert.assertArrayEquals(resolve(workdays.split(",")), festivalSchedule.getWorkdays());
    }


    private MonthDay resolve(String date) {
        return MonthDay.parse(date, DateTimeFormatterUtils.SHORT_MONTH_DAY);
    }

    private MonthDay[] resolve(String... dates) {
        return Arrays.stream(dates).map(this::resolve).toArray(MonthDay[]::new);
    }

}