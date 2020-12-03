package com.github.peacetrue.festivalschedule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tests for {@link FestivalScheduleUtils}
 *
 * @author xiayx
 */
public class FestivalScheduleUtilsTest {

    @Test
    public void resolveProperties() throws Exception {
        Year year = Year.of(2018);
        String date = "0101", restdays = "20171231,0102,0103", workdays = "0104,0105";
        Map<String, String> properties = new HashMap<>();
        properties.put(FestivalScheduleUtils.buildPropertyKey(year, Festival.NEW_YEAR, FestivalSchedule.PROPERTY_DATE), date);
        properties.put(FestivalScheduleUtils.buildPropertyKey(year, Festival.NEW_YEAR, FestivalSchedule.PROPERTY_RESTDAYS), restdays);
        properties.put(FestivalScheduleUtils.buildPropertyKey(year, Festival.NEW_YEAR, FestivalSchedule.PROPERTY_WORKDAYS), workdays);

        Map<Year, List<FestivalSchedule>> years = FestivalScheduleUtils.resolveProperties(properties);
        System.out.println(years);
        List<FestivalSchedule> festivalSchedules = years.get(year);
        FestivalSchedule festivalSchedule = festivalSchedules.stream()
                .filter(_festivalSchedule -> _festivalSchedule.getFestival().equals(Festival.NEW_YEAR))
                .findFirst().orElseThrow(IllegalArgumentException::new);
        Assertions.assertEquals(FestivalScheduleUtils.resolveTemporal(date), festivalSchedule.getDate());
        Assertions.assertArrayEquals(FestivalScheduleUtils.resolveTemporalArray(restdays), festivalSchedule.getRestdays());
        Assertions.assertArrayEquals(FestivalScheduleUtils.resolveTemporalArray(workdays), festivalSchedule.getWorkdays());
    }


}
