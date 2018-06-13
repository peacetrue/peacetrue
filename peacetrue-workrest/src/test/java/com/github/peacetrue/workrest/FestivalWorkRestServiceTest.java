package com.github.peacetrue.workrest;

import com.github.peacetrue.festivalschedule.FestivalScheduleUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * tests for {@link FestivalWorkRestService}
 *
 * @author xiayx
 */
public class FestivalWorkRestServiceTest {

    private FestivalWorkRestService festivalWorkRestService = new FestivalWorkRestService();
    private String yearOf2018 = "2018-01-01,2018-02-15,2018-02-16,2018-02-19,2018-02-20,2018-02-21,2018-04-05,2018-04-06,2018-04-30,2018-05-01,2018-06-18,2018-09-24,2018-10-01,2018-10-02,2018-10-03,2018-10-04,2018-10-05";

    public FestivalWorkRestServiceTest() {
        festivalWorkRestService.setFestivalScheduleProvider(FestivalScheduleUtils.buildFestivalScheduleProvider());
    }

    @Test
    public void isWorkday() throws Exception {
        Assert.assertFalse(festivalWorkRestService.isWorkday(LocalDate.parse("2018-06-12")));
    }

    @Test
    public void isRestday() throws Exception {
        Assert.assertFalse(festivalWorkRestService.isRestday(LocalDate.parse("2018-06-12")));
    }

    @Test
    public void getWorkdays() throws Exception {
        List<MonthDay> workdays = festivalWorkRestService.getWorkdays(Year.of(2018));
        //TODO finish Assert
    }

    @Test
    public void getRestdays() throws Exception {
        List<MonthDay> restdays = festivalWorkRestService.getRestdays(Year.of(2018));
        List<MonthDay> collect = Arrays.stream(yearOf2018.split(",")).map(s -> MonthDay.parse("--" + s.substring(5))).collect(Collectors.toList());
        Assert.assertTrue(restdays.containsAll(collect));
    }

}