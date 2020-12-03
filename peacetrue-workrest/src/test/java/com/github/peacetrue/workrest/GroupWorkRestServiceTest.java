package com.github.peacetrue.workrest;

import com.github.peacetrue.festivalschedule.FestivalScheduleUtils;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

/**
 * tests for {@link GroupWorkRestService}
 *
 * @author xiayx
 */
public class GroupWorkRestServiceTest {

    private static GroupWorkRestService workRestService = new GroupWorkRestService();

    @BeforeClass
    public static void beforeClass() throws Exception {
        FestivalWorkRestService festivalWorkRestService = new FestivalWorkRestService();
        festivalWorkRestService.setFestivalScheduleProvider(FestivalScheduleUtils.buildFestivalScheduleProvider());
        workRestService.setWorkRestServices(Arrays.asList(new WeekWorkRestService(), festivalWorkRestService));
    }

    @Test
    public void isWorkday() throws Exception {
        boolean workday = workRestService.isWorkday(LocalDate.now());
        System.out.println(workday);
    }

    @Test
    public void isRestday() throws Exception {
        boolean workday = workRestService.isRestday(LocalDate.now());
        System.out.println(workday);
    }

    @Test
    public void getWorkdays() throws Exception {
        List<MonthDay> workdays = workRestService.getWorkdays(Year.now());
        System.out.printf("workdays:%s", workdays);
    }

    @Test
    public void getRestdays() throws Exception {
        List<MonthDay> restdays = workRestService.getRestdays(Year.now());
        System.out.printf("restdays:%s", restdays);
    }

}
