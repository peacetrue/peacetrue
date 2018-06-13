package com.github.peacetrue.workrest;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * tests for {@link WeekWorkRestService}
 *
 * @author xiayx
 */
public class WeekWorkRestServiceTest {

    //Sample from calendar
    private List<LocalDate> weekdays = Stream.of(
            "2017-05-01", "2017-05-02", "2017-05-03", "2017-05-04", "2017-05-05",
            "2018-05-28", "2018-05-29", "2018-05-30", "2018-05-31", "2018-06-01",
            "2018-05-29"
    ).map(LocalDate::parse).collect(Collectors.toList());
    //Sample from calendar
    private List<LocalDate> weekends = Stream.of(
            "2017-05-06", "2017-05-07",
            "2017-05-27", "2017-05-28",
            "2018-05-05", "2018-05-06",
            "2017-05-28"
    ).map(LocalDate::parse).collect(Collectors.toList());

    private Map<Integer, List<LocalDate>> weekdayYears = this.weekdays.stream().collect(Collectors.groupingBy(LocalDate::getYear));
    private Map<Integer, List<LocalDate>> weekendYears = this.weekends.stream().collect(Collectors.groupingBy(LocalDate::getYear));

    private WeekWorkRestService weekWorkRestService = new WeekWorkRestService();

    @Test
    public void isWorkday() throws Exception {
        for (LocalDate weekday : weekdays) {
            //weekday is workday
            Assert.assertTrue(weekWorkRestService.isWorkday(weekday));
        }

        for (LocalDate weekend : weekends) {
            //weekend is not workday
            Assert.assertFalse(weekWorkRestService.isWorkday(weekend));
        }
    }

    @Test
    public void isRestday() throws Exception {
        for (LocalDate weekday : weekdays) {
            //weekday is not restday
            Assert.assertFalse(weekWorkRestService.isRestday(weekday));
        }

        for (LocalDate weekend : weekends) {
            //weekend is restday
            Assert.assertTrue(weekWorkRestService.isRestday(weekend));
        }
    }

    @Test
    public void getWorkdays() throws Exception {
        weekdayYears.forEach((year, date) -> {
            List<MonthDay> workdays = weekWorkRestService.getWorkdays(Year.of(year));
            //workdays contains all weekdays
            Assert.assertTrue(workdays.containsAll(date.stream().map(MonthDay::from).collect(Collectors.toList())));
        });

        weekendYears.forEach((year, date) -> {
            List<MonthDay> workdays = weekWorkRestService.getWorkdays(Year.of(year));
            //workdays contains none weekends
            Assert.assertFalse(workdays.removeAll(date.stream().map(MonthDay::from).collect(Collectors.toList())));
        });
    }

    @Test
    public void getRestdays() throws Exception {
        weekdayYears.forEach((year, date) -> {
            List<MonthDay> restdays = weekWorkRestService.getRestdays(Year.of(year));
            //restdays contains none weekdays
            Assert.assertFalse(restdays.removeAll(date.stream().map(MonthDay::from).collect(Collectors.toList())));
        });

        weekendYears.forEach((year, date) -> {
            List<MonthDay> restdays = weekWorkRestService.getRestdays(Year.of(year));
            //restdays contains all weekends
            Assert.assertTrue(restdays.containsAll(date.stream().map(MonthDay::from).collect(Collectors.toList())));
        });
    }

}